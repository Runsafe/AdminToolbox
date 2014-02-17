package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.OnlinePlayerRequired;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class GiveKit extends PlayerCommand
{
	public GiveKit(KitHandler handler)
	{
		super("give", "Give a kit to another player", "runsafe.toolbox.kits.give", new RequiredArgument("kit"), new OnlinePlayerRequired());
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.get("kit");
		if (!handler.kitExists(kitName))
			return "&cNo such kit exists.";

		if (!executor.hasPermission("runsafe.toolbox.kits.get." + kitName))
			return "&cYou do not have permission to give that kit.";

		IPlayer targetPlayer = parameters.getPlayer("player");
		if (targetPlayer == null)
			return "&cInvalid player.";

		handler.getKit(kitName, targetPlayer);
		return "&eKit '" + kitName + "' given to " + targetPlayer.getPlayerListName() + "&e.";
	}

	private final KitHandler handler;
}
