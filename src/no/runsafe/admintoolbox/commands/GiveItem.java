package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.PlayerArgument;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IAmbiguousPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.RunsafeItemStack;

import java.util.Map;

public class GiveItem extends ExecutableCommand
{
	public GiveItem(IServer server)
	{
		super(
				"give", "Give yourself or a player an item", "runsafe.toybox.give",
				new RequiredArgument("item"), new RequiredArgument("amount"), new PlayerArgument(false)
		);
		this.server = server;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, Map<String, String> parameters)
	{
		IPlayer player;
		if (!parameters.containsKey("player"))
		{
			if (executor instanceof IPlayer)
				player = (IPlayer) executor;
			else
				return "&cYou need to supply a player to give items to.";
		}
		else
		{
			player = server.getPlayer(parameters.get("player"));
			if (player == null)
				return "&cThat player does not exist.";

			if (player instanceof IAmbiguousPlayer)
				return player.toString();
		}

		RunsafeItemStack item = this.getItemId(parameters.get("item"));

		if (item == null)
			return "&cInvalid item name or ID.";

		int amount = Integer.valueOf(parameters.get("amount"));
		this.giveItems(player, item, amount);

		return String.format("&fGave %sx %s to %s&f.", amount, item.getNormalName(), player.getPrettyName());
	}

	private RunsafeItemStack getItemId(String itemName)
	{
		Item item = Item.get(itemName);
		return item == null ? null : item.getItem();
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

	private final IServer server;
}