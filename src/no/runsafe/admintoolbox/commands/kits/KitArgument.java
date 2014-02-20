package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.ITabComplete;
import no.runsafe.framework.api.command.argument.IValueExpander;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nullable;
import java.util.List;

public class KitArgument extends RequiredArgument implements ITabComplete, IValueExpander
{
	public KitArgument(KitHandler handler)
	{
		super("kit");
		this.handler = handler;
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial)
	{
		return handler.getAvailableKits(executor);
	}

	@Nullable
	@Override
	public String expand(ICommandExecutor context, @Nullable String value)
	{
		if (value == null)
			return null;

		List<String> kits;
		if (context instanceof IPlayer)
			kits = handler.getAvailableKits((IPlayer) context);
		else
			kits = handler.getAvailableKits(null);
		for (String kit : kits)
			if (kit.equalsIgnoreCase(value))
				return value;

		String partial = value.toLowerCase();
		for (String kit : kits)
			if (kit.toLowerCase().startsWith(partial))
				return kit;

		return null;
	}

	private final KitHandler handler;

}
