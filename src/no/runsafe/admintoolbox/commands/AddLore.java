package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.text.ChatColour;

public class AddLore extends PlayerCommand
{
	public AddLore()
	{
		super(
			"addlore",
			"Adds lore text to an item",
			"runsafe.toolbox.lore",
			new TrailingArgument("text")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta item = executor.getItemInMainHand();
		if (item != null && !item.is(Item.Unavailable.Air))
		{
			item.addLore(ChatColour.ToMinecraft(parameters.getValue("text")));
			return Config.Message.lore.addSucceed;
		}
		return Config.Message.playerNotHoldingItem;
	}
}
