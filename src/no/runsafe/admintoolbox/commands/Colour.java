package no.runsafe.admintoolbox.commands;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.RunsafeItemStack;
import no.runsafe.framework.minecraft.item.meta.RunsafeLeatherArmor;

public class Colour extends PlayerCommand
{
	public Colour()
	{
		super(
			"colour",
			"Colours an item you are holding.",
			"runsafe.toybox.colour",
			new RequiredArgument("hex")
		);
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		RunsafeItemStack item = executor.getItemInHand();
		if (item == null)
			return "&cInvalid item.";

		if (item.is(Item.Combat.Leggings.Leather) || item.is(Item.Combat.Boots.Leather) || item.is(Item.Combat.Chestplate.Leather) || item.is(Item.Combat.Helmet.Leather))
		{
			RunsafeLeatherArmor armor = (RunsafeLeatherArmor) item;
			if (parameters.getValue("hex").equals("random"))
				((RunsafeLeatherArmor) item).RandomColour();
			else
				armor.setColor(Integer.valueOf(parameters.getValue("hex"), 16));
			return "&2Coloured!";
		}
		return "&cYou cannot colour that item.";
	}
}