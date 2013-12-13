package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.handlers.VanishHandler;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

import java.util.Map;

public class Vanish extends PlayerCommand
{
	public Vanish(VanishHandler handler)
	{
		super("vanish", "Makes you invisible to players.", "runsafe.vanish.vanish");
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, Map<String, String> parameters)
	{
		if (handler.isHidden(executor))
		{
			handler.setHidden(executor, false);
			return "You are now visible again.";
		}
		else
		{
			handler.setHidden(executor, true);
			return "You are now hidden.";
		}
	}

	private final VanishHandler handler;
}
