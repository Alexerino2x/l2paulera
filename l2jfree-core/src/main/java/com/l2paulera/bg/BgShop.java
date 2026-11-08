package com.l2paulera.bg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.gameserver.datatables.ItemTable;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.items.templates.L2Item;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.network.packets.server.NpcHtmlMessage;

/**
 * Loja paga com pontos de rank. Itens em {@code Shop.Items} do battleground.properties:
 * {@code itemId:quantidade:preco;...}
 */
public final class BgShop
{
	private static final Log _log = LogFactory.getLog(BgShop.class);
	
	public record Offer(int itemId, long count, int price, String name)
	{
	}
	
	private static final BgShop INSTANCE = new BgShop();
	
	public static BgShop getInstance()
	{
		return INSTANCE;
	}
	
	private volatile List<Offer> _offers = new ArrayList<>();
	
	private BgShop()
	{
	}
	
	public void load(String spec)
	{
		final List<Offer> list = new ArrayList<>();
		for (String part : spec.split(";"))
		{
			if (part.isBlank())
				continue;
			try
			{
				final String[] f = part.trim().split(":");
				final int itemId = Integer.parseInt(f[0].trim());
				final long count = Long.parseLong(f[1].trim());
				final int price = Integer.parseInt(f[2].trim());
				final L2Item tpl = ItemTable.getInstance().getTemplate(itemId);
				if (tpl == null || count <= 0 || price <= 0)
				{
					_log.warn("Battleground loja: item invalido ignorado: " + part);
					continue;
				}
				list.add(new Offer(itemId, count, price, tpl.getName()));
			}
			catch (RuntimeException e)
			{
				_log.warn("Battleground loja: entrada mal formatada ignorada: " + part);
			}
		}
		_offers = list;
		_log.info("Battleground loja: " + list.size() + " itens");
	}
	
	public void show(L2Player player)
	{
		final int balance = RankService.getInstance().getBalance(player.getObjectId());
		final StringBuilder sb = new StringBuilder(2048);
		sb.append("<html><title>Loja de Rank</title><body><center><font color=LEVEL>Loja de Rank</font><br>");
		sb.append("Seus pontos: <font color=LEVEL>").append(balance).append("</font><br><br><table width=280>");
		final List<Offer> offers = _offers;
		for (int i = 0; i < offers.size(); i++)
		{
			final Offer o = offers.get(i);
			sb.append("<tr><td width=170>").append(o.name());
			if (o.count() > 1)
				sb.append(" x").append(o.count());
			sb.append("</td><td width=50>").append(o.price()).append("</td><td width=60>");
			if (balance >= o.price())
				sb.append("<a action=\"bypass -h bg_comprar ").append(i).append("\">Comprar</a>");
			else
				sb.append("<font color=808080>-</font>");
			sb.append("</td></tr>");
		}
		sb.append("</table><br><a action=\"bypass -h bg_menu\">Voltar</a></center></body></html>");
		player.sendPacket(new NpcHtmlMessage(0, sb.toString()));
	}
	
	public void buy(L2Player player, String indexText)
	{
		final List<Offer> offers = _offers;
		final int index;
		try
		{
			index = Integer.parseInt(indexText);
		}
		catch (RuntimeException e)
		{
			return;
		}
		if (index < 0 || index >= offers.size())
			return;
		final Offer o = offers.get(index);
		
		if (BattlegroundManager.getInstance().isInMatch(player))
		{
			player.sendMessage("Loja: termine a partida antes.");
			return;
		}
		if (BgConfig.REQUIRE_TOWN && !player.isInsideZone(L2Zone.FLAG_PEACE))
		{
			player.sendMessage("Loja: so na cidade.");
			return;
		}
		if (player.isDead() || player.isInStoreMode() || player.getActiveEnchantItem() != null)
		{
			player.sendMessage("Loja: voce nao pode comprar agora.");
			return;
		}
		final L2Item tpl = ItemTable.getInstance().getTemplate(o.itemId());
		final int slots = tpl.isStackable() ? 1 : (int)o.count();
		final long weight = (long)tpl.getWeight() * o.count();
		if (!player.getInventory().validateCapacity(slots) || weight > Integer.MAX_VALUE
				|| !player.getInventory().validateWeight((int)weight))
		{
			player.sendMessage("Loja: inventario cheio ou pesado demais.");
			return;
		}
		if (!RankService.getInstance().spend(player.getObjectId(), o.price()))
		{
			player.sendMessage("Loja: pontos insuficientes.");
			return;
		}
		try
		{
			player.addItem("BgShop", o.itemId(), o.count(), null, true);
		}
		catch (RuntimeException e)
		{
			_log.error("Battleground loja: falha ao entregar " + o + " para " + player.getName() + ", devolvendo pontos", e);
			RankService.getInstance().refund(player.getObjectId(), o.price());
			player.sendMessage("Loja: erro ao entregar o item, pontos devolvidos.");
			return;
		}
		RankService.getInstance().logPurchase(player.getObjectId(), o.itemId(), o.count(), o.price());
		show(player);
	}
}
