package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.admintoolbox.binding.BindingHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class Bind extends PlayerCommand
{
	public Bind(BindingHandler handler)
	{
		super(
			"bind",
			"Bind a command to an item type.",
			"runsafe.admintoolbox.bind.temporary",
			new TrailingArgument("commands")
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta handItem = executor.getItemInMainHand();

		if (handItem == null)
			return Config.Message.playerNotHoldingItem;

		Item itemType = handItem.getItemType();
		String commandString = parameters.getValue("commands");

		if (commandString == null || commandString.equalsIgnoreCase("none"))
		{
			handler.removeBinding(executor, itemType);
			return Config.Message.bind.setUnbind;
		}

		handler.addBinding(executor, itemType, commandString.split("\\\\"));
		return Config.Message.bind.setSucceed;
	}

	private final BindingHandler handler;
}
