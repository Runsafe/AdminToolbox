package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.Duration;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.tools.TimeFormatter;

public class SetCooldownTime extends ExecutableCommand
{
	public SetCooldownTime(KitHandler handler)
	{
		super(
			"setcooldowntime",
			"Sets the cooldown time for a kit",
			"runsafe.toolbox.kits.setcooldown",
			new KitArgument(handler), new Duration("cooldown")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String kitName = parameters.getRequired("kit");
		java.time.Duration cooldown = parameters.getValue("cooldown");

		if (cooldown == null)
			cooldown = java.time.Duration.ZERO;

		handler.changeKitCooldown(kitName, cooldown);

		if (cooldown.isZero())
			return String.format("&aRemoved cooldown timer for kit&r: %s", kitName);
		return String.format(
			"&aSet cooldown timer for kit %s to %s.",
			kitName, TimeFormatter.formatDuration(cooldown)
		);
	}

	private final KitHandler handler;
}
