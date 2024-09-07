package no.runsafe.admintoolbox.commands.kits;

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
			return "&cInvalid kit name: Must be A-Z, a-z, 0-9 and between 1-20 chars.";
		if (cooldown == null)
			cooldown = java.time.Duration.ZERO;

		handler.createKit(kitName, executor, universeName, cooldown);
		if (universeName == null)
			return String.format("&eKit '%s' created with no cooldown that can be redeemed anywhere.", kitName);

		return String.format(
			"&eKit &r%s'&e created for universe &r%s&e with a cooldown time of &r%s",
			kitName, universeName, TimeFormatter.formatDuration(cooldown)
		);
	}

	private final KitHandler handler;
	private final Pattern kitNamePattern = Pattern.compile("^[A-Za-z0-9]{1,20}$");
}
