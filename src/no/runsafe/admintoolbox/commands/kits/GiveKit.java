package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class GiveKit extends PlayerCommand
{
	public GiveKit(KitHandler handler)
	{
		super("give", "Give a kit to another player", "runsafe.toolbox.kits.give", new KitArgument(handler), new Player().onlineOnly().require());
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");
		if (!handler.canUseKit(executor, kitName))
			return "&cYou do not have permission to give that kit.";

		IPlayer targetPlayer = parameters.getValue("player");
		assert(targetPlayer != null);
		handler.getKit(kitName, targetPlayer);
		return "&eKit '" + kitName + "' given to " + targetPlayer.getPlayerListName() + "&e.";
	}

	private final KitHandler handler;
}
