package com.l2paulera.bg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.config.L2Properties;
import com.l2jfree.gameserver.model.Location;

/**
 * Configuracao do motor de battlegrounds: {@code config/battleground.properties}
 * (com override em {@code config/local/battleground.properties}).
 */
public final class BgConfig
{
	private static final Log _log = LogFactory.getLog(BgConfig.class);
	
	public static final String FILE = "./config/battleground.properties";
	
	public static boolean ENABLED;
	public static int MIN_LEVEL;
	public static boolean REQUIRE_TOWN;
	public static int QUEUE_COUNTDOWN_SECONDS;
	public static int PREPARE_SECONDS;
	public static int RESPAWN_SECONDS;
	public static int END_RETURN_SECONDS;
	public static Location DEFAULT_RETURN;
	
	public static int POINTS_WIN;
	public static int POINTS_LOSS;
	public static int POINTS_DRAW;
	public static int POINTS_PER_KILL;
	public static int POINTS_KILL_CAP;
	
	/** itemId:quantidade:preco;... */
	public static String SHOP_ITEMS;
	
	private static L2Properties _props = new L2Properties();
	
	private BgConfig()
	{
	}
	
	public static void load()
	{
		try
		{
			_props = new L2Properties(FILE);
		}
		catch (Exception e)
		{
			_log.warn("Battleground: nao consegui ler " + FILE + ", usando valores padrao", e);
			_props = new L2Properties();
		}
		_props.setLog(false);
		
		ENABLED = _props.getBool("Enabled", true);
		MIN_LEVEL = _props.getInteger("MinLevel", 40);
		REQUIRE_TOWN = _props.getBool("RequireTown", true);
		QUEUE_COUNTDOWN_SECONDS = _props.getInteger("QueueCountdownSeconds", 30);
		PREPARE_SECONDS = _props.getInteger("PrepareSeconds", 20);
		RESPAWN_SECONDS = _props.getInteger("RespawnSeconds", 8);
		END_RETURN_SECONDS = _props.getInteger("EndReturnSeconds", 10);
		DEFAULT_RETURN = parseLoc(_props.getString("DefaultReturn", "83400,148600,-3400"));
		
		POINTS_WIN = _props.getInteger("Points.Win", 30);
		POINTS_LOSS = _props.getInteger("Points.Loss", 10);
		POINTS_DRAW = _props.getInteger("Points.Draw", 15);
		POINTS_PER_KILL = _props.getInteger("Points.PerKill", 2);
		POINTS_KILL_CAP = _props.getInteger("Points.KillCap", 20);
		
		SHOP_ITEMS = _props.getString("Shop.Items", "");
	}
	
	// ---- por modo: <Id>.<Chave> ----------------------------------------------------------
	
	public static boolean isModeEnabled(String mode)
	{
		return _props.getBool(key(mode, "Enabled"), true);
	}
	
	public static int getMinPlayers(String mode, int deflt)
	{
		return _props.getInteger(key(mode, "MinPlayers"), deflt);
	}
	
	public static int getMaxPlayers(String mode, int deflt)
	{
		return _props.getInteger(key(mode, "MaxPlayers"), deflt);
	}
	
	public static int getDurationSeconds(String mode, int defltMinutes)
	{
		return _props.getInteger(key(mode, "DurationMinutes"), defltMinutes) * 60;
	}
	
	public static int getInt(String mode, String name, int deflt)
	{
		return _props.getInteger(key(mode, name), deflt);
	}
	
	/** lista "x,y,z;x,y,z;..." */
	public static List<Location> getLocations(String mode, String name, String deflt)
	{
		final List<Location> list = new ArrayList<>();
		for (String part : _props.getString(key(mode, name), deflt).split(";"))
		{
			if (!part.isBlank())
				list.add(parseLoc(part));
		}
		return list;
	}
	
	private static String key(String mode, String name)
	{
		// TvT.MinPlayers, Deathmatch.MinPlayers... o id do modo e case-insensitive no arquivo
		for (Object k : _props.keySet())
		{
			final String s = k.toString();
			if (s.equalsIgnoreCase(mode + "." + name))
				return s;
		}
		return mode + "." + name;
	}
	
	static Location parseLoc(String text)
	{
		final String[] p = text.trim().split(",");
		return new Location(Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()), Integer.parseInt(p[2].trim()));
	}
}
