package no.runsafe.admintoolbox.events;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.player.IPlayerMove;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class PlayerMove implements IPlayerMove
{
	@Override
	public boolean OnPlayerMove(IPlayer player, ILocation from, ILocation to)
	{
		if (!player.isGliding() || Config.canElytraFly(player))
			return true;

		player.setGliding(false);
		player.sendColouredMessage(Config.Message.elytraFail);

		RunsafeMeta chestplate = player.getChestplate();
		if (chestplate != null && chestplate.is(Item.Transportation.Elytra))
		{
			chestplate.setDurability((short) (chestplate.getDurability() + 50));
			player.setChestplate(chestplate);
		}

		return true;
	}
}
