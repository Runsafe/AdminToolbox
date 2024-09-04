package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.Duration;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

import java.util.regex.Pattern;

public class CreateKit extends PlayerCommand
{
	public CreateKit(KitHandler handler)
	{
		super(
			"create",
			"Create a kit",
			"runsafe.toolbox.kits.create",
			new RequiredArgument("kit"), new Duration("cooldown")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		String kitName = parameters.getRequired("kit");
		java.time.Duration cooldown = parameters.getValue("cooldown");
		if (!clanNamePattern.matcher(kitName).matches())
			return "&cInvalid kit name: Must be A-Z, a-z, 0-9 and between 1-20 chars.";

		handler.createKit(kitName, executor, cooldown);
		if (cooldown == null)
			return String.format("&eKit '%s' created.", kitName);

		return String.format(
			"&eKit '%s' created with a cooldown time of %s",
			kitName, cooldown.toString().replace("PT", "")
		);
	}

	private final KitHandler handler;
	private final Pattern clanNamePattern = Pattern.compile("^[A-Za-z0-9]{1,20}$");
}
