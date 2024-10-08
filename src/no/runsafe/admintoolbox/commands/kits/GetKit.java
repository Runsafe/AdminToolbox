package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class GetKit extends PlayerCommand
{
	public GetKit(KitHandler handler)
	{
		super(
			"get",
			"Get a kit",
			"runsafe.toolbox.kits.get",
			new KitArgument(handler)
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");

		return handler.getKit(kitName, executor);
	}

	private final KitHandler handler;
}
