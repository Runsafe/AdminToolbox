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
		if (handler.isInvalidKit(kitName))
			return "&cNo such kit exists.";

		if (!handler.canUseKit(executor, kitName))
			return "&cYou do not have permission to get that kit.";

		handler.getKit(kitName, executor);
		return null;
	}

	private final KitHandler handler;
}
