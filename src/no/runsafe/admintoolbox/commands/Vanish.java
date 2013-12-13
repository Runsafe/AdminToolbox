package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

import java.util.Map;

public class Vanish extends PlayerCommand
{
	public Vanish()
	{
		super("vanish", "Makes you invisible to players.", "runsafe.toolbox.vanish");
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		return "Nope, sorry, nothing.";
	}
}
