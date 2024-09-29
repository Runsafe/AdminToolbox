package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.Config;
import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.Duration;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.OptionalArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.tools.TimeFormatter;

import java.util.regex.Pattern;

public class CreateKit extends PlayerCommand
{
	public CreateKit(KitHandler handler)
	{
		super(
			"create",
			"Create a kit",
			"runsafe.toolbox.kits.create",
			new RequiredArgument("kit"), new OptionalArgument("UniverseName"), new Duration("cooldown")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.getRequired("kit");
		java.time.Duration cooldown = parameters.getValue("cooldown");
		String universeName = parameters.getValue("UniverseName");
		if (!kitNamePattern.matcher(kitName).matches())
			return Config.Message.Kit.invalidName;
		if (cooldown == null)
			cooldown = java.time.Duration.ZERO;

		handler.createKit(kitName, universeName, cooldown);
		if (universeName == null)
			executor.sendColouredMessage(Config.Message.Kit.created, kitName);
		else
			executor.sendColouredMessage(
				Config.Message.Kit.created2,
				kitName, universeName, TimeFormatter.formatDuration(cooldown)
			);

		return handler.editKitInventory(executor, kitName);
	}

	private final KitHandler handler;
	private final Pattern kitNamePattern = Pattern.compile("^[A-Za-z0-9]{1,20}$");
}
