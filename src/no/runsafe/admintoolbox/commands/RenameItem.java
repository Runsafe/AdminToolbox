package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.text.ChatColour;

public class RenameItem extends PlayerCommand
{
	public RenameItem()
	{
		super(
			"renameitem",
			"Renames the item you are holding",
			"runsafe.toybox.rename",
			new TrailingArgument("name")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeMeta item = executor.getItemInMainHand();
		if (item == null || item.is(Item.Unavailable.Air))
			return Config.Message.playerNotHoldingItem;

		String name = ChatColour.ToMinecraft(parameters.getValue("name"));
		item.setDisplayName(name);
		if (name.equals(item.getDisplayName()))
			return Config.Message.renameItem.succeed;

		return Config.Message.renameItem.fail;
	}
}

