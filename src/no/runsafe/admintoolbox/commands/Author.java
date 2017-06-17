package no.runsafe.admintoolbox.commands;

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
		RunsafeItemStack item = executor.getItemInHand();
		if (item != null && item.is(Item.Special.Crafted.WrittenBook))
		{
			String author = ChatColour.ToMinecraft((String) parameters.getValue("author"));
			((RunsafeBook) item).setAuthor(author);
			return "&2Author changed to " + author;
		}
		return "&cYou cannot change the author of that item.";
	}
}
