package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.player.IPlayer;

public class GiveKit extends ExecutableCommand
{
	public GiveKit(KitHandler handler)
	{
		super(
			"give",
			"Give a kit to another player",
			"runsafe.toolbox.kits.give",
			new KitArgument(handler),
			new Player().onlineOnly().require()
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");
		IPlayer targetPlayer = parameters.getRequired("player");

		handler.giveKit(kitName, targetPlayer);
		return String.format("&eKit &r%s&e given to %s &e.", kitName, targetPlayer.getPrettyName());
	}

	private final KitHandler handler;
}
