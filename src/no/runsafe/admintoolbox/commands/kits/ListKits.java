package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ListKits extends PlayerCommand
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
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		List<String> kits = handler.getAvailableKits(executor);
		if (kits.isEmpty())
			return "&cYou have no available kits.";

		return "&aAvailable Kits: &f" + StringUtils.join(kits, ", ");
	}

	private final KitHandler handler;
}