package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class ClearLore extends PlayerCommand
{
	public ClearLore()
	{
		super(
			"clearlore",
			"Clears all lore from an item.",
			"runsafe.toolbox.lore"
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta item = executor.getItemInMainHand();
		if (item != null && !item.is(Item.Unavailable.Air))
		{
			item.clearLore();
			return "&eLore text cleared.";
		}
		return "&cInvalid item in hand.";
	}
}
