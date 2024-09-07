package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class Repair extends PlayerCommand
{
	public Repair()
	{
		super(
			"repair",
			"Repair the item you are holding",
			"runsafe.toolbox.repair"
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta item = executor.getItemInMainHand();
		if (item == null)
			return Config.Message.playerNotHoldingItem;

		item.setDurability((short) 0);
		return Config.Message.repair.succeed;
	}
}