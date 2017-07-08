package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class BindPermanent extends PlayerCommand
{
	public BindPermanent()
	{
		super(
			"bindpermanent",
			"Bind a command to an item permanently.",
			"runsafe.admintoolbox.bind.permanent",
			new TrailingArgument("commands")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta handItem = executor.getItemInHand();

		if (handItem == null)
			return "&cYou need to bind to an item";

		String commandString = parameters.getValue("commands");
		handItem.setTagCompound("runsafe.bound-commands", commandString);
		return "&aBound!";
	}
}
