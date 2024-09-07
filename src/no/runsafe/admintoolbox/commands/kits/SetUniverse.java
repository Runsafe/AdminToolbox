package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.OptionalArgument;

public class SetUniverse extends ExecutableCommand
{
	public SetUniverse(KitHandler handler)
	{
		super(
			"setuniverse",
			"Sets the universe for a kit to be used in",
			"runsafe.toolbox.kits.setuniverse",
			new KitArgument(handler), new OptionalArgument("UniverseName")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String kitName = parameters.getRequired("kit");
		String universeName = parameters.getValue("UniverseName");

		handler.setKitUniverse(kitName, universeName);

		if (universeName == null)
			return String.format("&aRemoved universe requirement for kit &r%s", kitName);

		return String.format("&aSet universe for kit &r%s&a to &r%s", kitName, universeName);
	}

	private final KitHandler handler;
}
