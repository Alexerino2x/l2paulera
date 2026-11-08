package com.l2paulera.bg;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.L2DatabaseFactory;

/**
 * Pontos de rank por tipo de evento ({@code bg_rank}), carteira da loja ({@code bg_wallet}) e historico de
 * partidas ({@code bg_match}). Cada ponto ganho entra no rank do modo (nunca diminui) e na carteira (a loja gasta).
 */
public final class RankService
{
	private static final Log _log = LogFactory.getLog(RankService.class);
	
	/** modo especial usado para consultar o rank geral (soma de todos os modos) */
	public static final String OVERALL = "geral";
	
	private static final RankService INSTANCE = new RankService();
	
	public static RankService getInstance()
	{
		return INSTANCE;
	}
	
	private RankService()
	{
	}
	
	public record Entry(String name, int points, int wins, int matches, int kills, int deaths)
	{
	}
	
	/** quantos pontos uma partida da a um participante */
	public static int pointsFor(Participant p, boolean won, boolean draw)
	{
		int pts = draw ? BgConfig.POINTS_DRAW : won ? BgConfig.POINTS_WIN : BgConfig.POINTS_LOSS;
		pts += Math.min(p.getKills() * BgConfig.POINTS_PER_KILL, BgConfig.POINTS_KILL_CAP);
		return pts;
	}
	
	void recordMatch(Match match, List<Participant> winners)
	{
		final String mode = match.getMode().getId();
		final boolean draw = winners.isEmpty();
		final Set<Integer> winnerIds = new HashSet<>();
		for (Participant w : winners)
			winnerIds.add(w.getObjectId());
		
		final String sql =
				"INSERT INTO bg_rank (char_id, mode, points, matches, wins, losses, kills, deaths) "
						+ "VALUES (?,?,?,1,?,?,?,?) ON DUPLICATE KEY UPDATE points=points+VALUES(points), "
						+ "matches=matches+1, wins=wins+VALUES(wins), "
						+ "losses=losses+VALUES(losses), kills=kills+VALUES(kills), deaths=deaths+VALUES(deaths)";
		final String walletSql =
				"INSERT INTO bg_wallet (char_id, balance) VALUES (?,?) ON DUPLICATE KEY UPDATE balance=balance+VALUES(balance)";
		
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql); PreparedStatement wallet = con.prepareStatement(walletSql))
			{
				for (Participant p : match.getParticipants())
				{
					// quem saiu no meio nao ganha nada, mas a derrota conta
					final boolean won = !p.hasLeft() && winnerIds.contains(p.getObjectId());
					final int pts = p.hasLeft() ? 0 : pointsFor(p, won, draw);
					
					ps.setInt(1, p.getObjectId());
					ps.setString(2, mode);
					ps.setInt(3, pts);
					ps.setInt(4, won ? 1 : 0);
					ps.setInt(5, !won && !draw ? 1 : 0);
					ps.setInt(6, p.getKills());
					ps.setInt(7, p.getDeaths());
					ps.addBatch();
					
					if (pts > 0)
					{
						wallet.setInt(1, p.getObjectId());
						wallet.setInt(2, pts);
						wallet.addBatch();
					}
					
					if (pts > 0 && p.getPlayer() != null)
						p.getPlayer().sendMessage("Battleground: +" + pts + " pontos de rank em " + match.getMode().getDisplayName() + ".");
				}
				ps.executeBatch();
				wallet.executeBatch();
			}
			try (PreparedStatement ps =
					con.prepareStatement("INSERT INTO bg_match (mode, ended_at, players, result) VALUES (?, NOW(), ?, ?)"))
			{
				ps.setString(1, mode);
				ps.setInt(2, match.getParticipants().size());
				ps.setString(3, draw ? "empate" : winners.size() == 1 ? winners.get(0).getName() : "time " + Match.getTeamName(winners.get(0).getTeam()));
				ps.executeUpdate();
			}
		}
		catch (SQLException e)
		{
			_log.error("Battleground: erro ao gravar a partida", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	/** top N de um modo (ou {@link #OVERALL}) por pontos ganhos */
	public List<Entry> getTop(String mode, int limit)
	{
		final boolean overall = OVERALL.equals(mode);
		final String sql =
				"SELECT c.char_name, SUM(r.points) pts, SUM(r.wins) w, SUM(r.matches) m, SUM(r.kills) k, SUM(r.deaths) d "
						+ "FROM bg_rank r JOIN characters c ON c.charId = r.char_id "
						+ (overall ? "" : "WHERE r.mode = ? ")
						+ "GROUP BY r.char_id, c.char_name ORDER BY pts DESC, w DESC LIMIT ?";
		final List<Entry> list = new ArrayList<>();
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps = con.prepareStatement(sql))
			{
				int i = 1;
				if (!overall)
					ps.setString(i++, mode);
				ps.setInt(i, limit);
				try (ResultSet rs = ps.executeQuery())
				{
					while (rs.next())
						list.add(new Entry(rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
				}
			}
		}
		catch (SQLException e)
		{
			_log.error("Battleground: erro ao ler o rank", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
		return list;
	}
	
	/** saldo da carteira (pontos para a loja) */
	public int getBalance(int charId)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps = con.prepareStatement("SELECT balance FROM bg_wallet WHERE char_id=?"))
			{
				ps.setInt(1, charId);
				try (ResultSet rs = ps.executeQuery())
				{
					return rs.next() ? rs.getInt(1) : 0;
				}
			}
		}
		catch (SQLException e)
		{
			_log.error("Battleground: erro ao ler saldo", e);
			return 0;
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	/**
	 * Debita a carteira de forma atomica.
	 * 
	 * @return true se havia saldo e o valor foi debitado
	 */
	public boolean spend(int charId, int amount)
	{
		return changeBalance(charId, -amount);
	}
	
	/** devolve pontos (ex.: compra que falhou) */
	public void refund(int charId, int amount)
	{
		changeBalance(charId, amount);
	}
	
	private boolean changeBalance(int charId, int delta)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps =
					con.prepareStatement("UPDATE bg_wallet SET balance=balance+? WHERE char_id=? AND balance+? >= 0"))
			{
				ps.setInt(1, delta);
				ps.setInt(2, charId);
				ps.setInt(3, delta);
				return ps.executeUpdate() == 1;
			}
		}
		catch (SQLException e)
		{
			_log.error("Battleground: erro ao alterar saldo de " + charId, e);
			return false;
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
	
	void logPurchase(int charId, int itemId, long count, int price)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			try (PreparedStatement ps = con.prepareStatement(
					"INSERT INTO bg_shop_log (char_id, item_id, count, price, bought_at) VALUES (?,?,?,?,NOW())"))
			{
				ps.setInt(1, charId);
				ps.setInt(2, itemId);
				ps.setLong(3, count);
				ps.setInt(4, price);
				ps.executeUpdate();
			}
		}
		catch (SQLException e)
		{
			_log.error("Battleground: erro ao registrar compra", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}
}
