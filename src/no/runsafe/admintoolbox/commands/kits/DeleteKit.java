package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;

public class DeleteKit extends ExecutableCommand
{
	public DeleteKit(KitHandler handler)
	{
		super(
			"delete",
			"Delete a kit",
			"runsafe.toolbox.kits.delete",
			new KitArgument(handler)
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");

		handler.deleteKit(kitName);
		return "&eKit '" + kitName + "' has been deleted.";
	}

	private final KitHandler handler;
}
