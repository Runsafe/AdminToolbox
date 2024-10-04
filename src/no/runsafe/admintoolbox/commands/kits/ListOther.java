package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.Config;
import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.player.IPlayer;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ListOther extends ExecutableCommand
{
	public ListOther(KitHandler handler)
	{
		super(
			"listother",
			"List all kits available to a specific online player",
			"runsafe.toolbox.kits.list.other",
			new Player().onlineOnly().require()
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer target = parameters.getRequired("player");

		List<String> kits = handler.getAvailableKits(target);
		if (kits.isEmpty())
			return Config.Message.Kit.noneAvailable;

		return String.format(Config.Message.Kit.availableOther, target.getPrettyName()) +
			StringUtils.join(kits, Config.Message.Kit.availableSeparator);
	}

	private final KitHandler handler;
}