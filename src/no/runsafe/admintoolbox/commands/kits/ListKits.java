package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ListKits extends ExecutableCommand
{
	public ListKits(KitHandler handler)
	{
		super(
			"list",
			"List all kits available to you",
			"runsafe.toolbox.kits.list"
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		List<String> kits = handler.getAvailableKits(executor);
		if (kits.isEmpty())
			return "&cYou have no available kits.";

		return "&aAvailable Kits: &f" + StringUtils.join(kits, "&r, &f");
	}

	private final KitHandler handler;
}