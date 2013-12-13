package no.runsafe.admintoolbox.handlers;

import no.runsafe.admintoolbox.events.VanishEvent;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.packets.PacketPlayerInfo;

import java.util.ArrayList;
import java.util.List;

public class VanishHandler
{
	public VanishHandler(IConsole console, IServer server)
	{
		this.console = console;
		this.server = server;
	}

	public boolean isHidden(IPlayer player, IPlayer context)
	{
		return isHidden(player) && !playerCanSeeHiddenPlayers(context);
	}

	public boolean isHidden(IPlayer player)
	{
		return hiddenPlayers.contains(player.getName());
	}

	public void setHidden(IPlayer player, boolean setHidden)
	{
		boolean isHidden = isHidden(player);

		if (setHidden && !isHidden)
		{
			// Vanish the player.
			hiddenPlayers.add(player.getName());
			console.logInformation("%s has disappeared.", player.getPrettyName());
			processPlayer(player, true);
		}
		else if (!setHidden && isHidden)
		{
			// Un-vanish the player.
			hiddenPlayers.remove(player.getName());
			console.logInformation("%s has reappeared.", player.getPrettyName());
			processPlayer(player, false);
		}
	}

	private void processPlayer(IPlayer player, boolean isHidden)
	{
		PacketPlayerInfo packet = new PacketPlayerInfo(player.getPlayerListName(), !isHidden, 1000);

		for (IPlayer onlinePlayer : server.getOnlinePlayers())
			if (!playerCanSeeHiddenPlayers(onlinePlayer))
				packet.send(onlinePlayer);

		new VanishEvent(player, isHidden).Fire();
	}

	private boolean playerCanSeeHiddenPlayers(IPlayer player)
	{
		return player.hasPermission("runsafe.vanish.see");
	}

	private final IConsole console;
	private final IServer server;
	private final List<String> hiddenPlayers = new ArrayList<String>();
}
