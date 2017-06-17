package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class DeleteKit extends PlayerCommand
{
	public DeleteKit(KitHandler handler)
	{
		super(
			"delete",
			"Delete a kit",
			"runsafe.toolbox.kits.delete",
			new RequiredArgument("kit")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");
		if (!handler.kitExists(kitName))
			return "&cNo such kit exists.";

		handler.deleteKit(kitName);
		return "&eKit '" + kitName + "' has been deleted.";
	}

	private final KitHandler handler;
}
