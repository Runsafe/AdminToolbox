package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.Player;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.WholeNumber;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.exceptions.UnknownMaterialException;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.RunsafeItemStack;

public class GiveItem extends ExecutableCommand
{
	public GiveItem()
	{
		super(
			"give",
			"Give yourself or a player an item",
			"runsafe.toybox.give",
			new RequiredArgument("item"),
			new WholeNumber("amount").withDefault(1),
			new Player().onlineOnly().defaultToExecutor()
		);
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		IPlayer player = parameters.getValue("player");
		if (player == null)
			return null;

		RunsafeItemStack item = this.getItemId(parameters.getValue("item"));

		if (item == null)
			return Config.Message.give.failInvalidItem;
		if (item.is(Item.Unavailable.Air))
			return Config.Message.give.failAir;

		Integer amount = parameters.getValue("amount");
		assert (amount != null);
		this.giveItems(player, item, amount);
		return String.format(Config.Message.give.succeed, amount, item.getNormalName(), player.getPrettyName());
	}

	private RunsafeItemStack getItemId(String itemName)
	{
		try
		{
			Item item = Item.get(itemName);
			return item == null ? null : item.getItem();
		}
		catch (UnknownMaterialException e)
		{
			return null;
		}
	}

	private void giveItems(IPlayer player, RunsafeItemStack item, int amount)
	{
		int needed = amount;
		while (needed > 0)
		{
			if (needed < item.getMaxStackSize())
			{
				item.setAmount(needed);
				needed = 0;
			}
			else
			{
				item.setAmount(item.getMaxStackSize());
				needed -= item.getMaxStackSize();
			}
			player.getInventory().addItems(item.clone());
		}
		player.updateInventory();
	}
}