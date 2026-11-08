package com.l2paulera.bg;

import com.l2jfree.gameserver.gameobjects.L2Creature;
import com.l2jfree.gameserver.gameobjects.L2Npc;
import com.l2jfree.gameserver.gameobjects.L2Playable;
import com.l2jfree.gameserver.gameobjects.L2Player;
import com.l2jfree.gameserver.model.restriction.global.AbstractRestriction;
import com.l2jfree.gameserver.model.restriction.global.GlobalRestriction;
import com.l2jfree.gameserver.model.restriction.global.GlobalRestrictions.CombatState;
import com.l2jfree.gameserver.model.zone.L2Zone;

/**
 * Liga o motor de battlegrounds aos ganchos do core (mortes, PvP, teleporte, zonas, comandos).
 */
public final class BattlegroundRestriction extends AbstractRestriction
{
	private static final class SingletonHolder
	{
		private static final BattlegroundRestriction INSTANCE = new BattlegroundRestriction();
	}
	
	public static BattlegroundRestriction getInstance()
	{
		return SingletonHolder.INSTANCE;
	}
	
	private BattlegroundRestriction()
	{
	}
	
	private static BattlegroundManager mgr()
	{
		return BattlegroundManager.getInstance();
	}
	
	@Override
	public boolean isRestricted(L2Player activeChar, Class<? extends GlobalRestriction> callingRestriction)
	{
		if (callingRestriction == getClass())
			return false;
		if (mgr().isInMatch(activeChar) || mgr().getQueuedMode(activeChar) != null)
		{
			activeChar.sendMessage("Voce esta em uma battleground (ou na fila). Use .bg sair antes.");
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canRequestRevive(L2Player activeChar)
	{
		// o motor cuida do renascimento
		return !mgr().isInMatch(activeChar);
	}
	
	@Override
	public boolean canTeleport(L2Player activeChar)
	{
		if (mgr().isInMatch(activeChar))
		{
			activeChar.sendMessage("Use .bg sair para sair da partida.");
			return false;
		}
		return true;
	}
	
	@Override
	public boolean canInviteToParty(L2Player activeChar, L2Player target)
	{
		final Match m1 = mgr().getMatch(activeChar);
		final Match m2 = mgr().getMatch(target);
		if (m1 == null && m2 == null)
			return true;
		if (m1 != m2)
			return false;
		final Participant a = m1.getParticipant(activeChar.getObjectId());
		final Participant b = m1.getParticipant(target.getObjectId());
		return a != null && b != null && a.getTeam() >= 0 && a.getTeam() == b.getTeam();
	}
	
	@Override
	public CombatState getCombatState(L2Player activeChar, L2Player target)
	{
		final Match m1 = mgr().getMatch(activeChar);
		final Match m2 = mgr().getMatch(target);
		if (m1 == null && m2 == null)
			return CombatState.NEUTRAL;
		if (m1 != m2 || !m1.isCombatOpen())
			return CombatState.FRIEND; // quem esta fora nao briga com quem esta dentro, e ninguem briga antes de liberar
		final Participant a = m1.getParticipant(activeChar.getObjectId());
		final Participant b = m1.getParticipant(target.getObjectId());
		if (a == null || b == null)
			return CombatState.FRIEND;
		if (a.getTeam() >= 0 && a.getTeam() == b.getTeam())
			return CombatState.FRIEND;
		return CombatState.ENEMY;
	}
	
	@Override
	public int getNameColor(L2Player activeChar)
	{
		final Match m = mgr().getMatch(activeChar);
		if (m == null)
			return -1;
		final Participant p = m.getParticipant(activeChar.getObjectId());
		return p == null ? -1 : m.getNameColor(p);
	}
	
	@Override
	public Boolean isInsideZone(L2Creature activeChar, byte zone)
	{
		if (!(activeChar instanceof L2Playable))
			return null;
		final Match m = mgr().getMatch(activeChar.getActingPlayer());
		if (m == null || activeChar.getInstanceId() != m.getInstanceId())
			return null;
		
		switch (zone)
		{
			case L2Zone.FLAG_NOSUMMON:
				return Boolean.TRUE;
			case L2Zone.FLAG_PEACE:
				return !m.isCombatOpen();
			case L2Zone.FLAG_PVP:
				return m.isCombatOpen();
		}
		return null;
	}
	
	@Override
	public boolean playerKilled(L2Creature activeChar, L2Player target, L2Player killer)
	{
		final Match m = mgr().getMatch(target);
		if (m == null)
			return false;
		// so conta como abate se o assassino estiver na mesma partida
		m.onKill(killer != null && mgr().getMatch(killer) == m ? killer : null, target);
		return true; // sem karma, sem pvp count, sem perda de exp pela regra normal
	}
	
	@Override
	public boolean onAction(L2Npc npc, L2Player activeChar)
	{
		final Match m = mgr().getMatch(activeChar);
		return m != null && m.onNpcAction(activeChar, npc);
	}
	
	@Override
	public void playerLoggedIn(L2Player activeChar)
	{
		mgr().onLogin(activeChar);
	}
	
	@Override
	public void playerDisconnected(L2Player activeChar)
	{
		mgr().onDisconnect(activeChar);
	}
	
	@Override
	public boolean useVoicedCommand(String command, L2Player activeChar, String target)
	{
		if (!"bg".equals(command))
			return false;
		mgr().onCommand(activeChar, target);
		return true;
	}
}
