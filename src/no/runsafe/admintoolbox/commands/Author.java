package no.runsafe.admintoolbox.commands;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.RunsafeItemStack;
import no.runsafe.framework.minecraft.item.meta.RunsafeBook;
import no.runsafe.framework.text.ChatColour;

public class Author extends PlayerCommand
{
	public Author()
	{
		super(
			"author",
			"Changes the author of the book you are holding",
			"runsafe.toybox.author",
			new TrailingArgument("author")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeItemStack item = executor.getItemInMainHand();
		if (item == null || item.is(Item.Unavailable.Air))
			return Config.Message.playerNotHoldingItem;

		if (item.is(Item.Special.Crafted.WrittenBook))
		{
			String author = ChatColour.ToMinecraft(parameters.getValue("author"));
			((RunsafeBook) item).setAuthor(author);
			return String.format(Config.Message.author.setSucceed, author);
		}
		return Config.Message.author.setFail;
	}
}
