package no.runsafe.admintoolbox.events;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.entity.IEntityToggleGlideEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityToggleGlideEvent;

public class EntityToggleGlideEvent implements IEntityToggleGlideEvent
{
	@Override
	public boolean OnEntityGlide(RunsafeEntityToggleGlideEvent event)
	{
		IEntity entity = event.getEntity();
		if (!(entity instanceof IPlayer) || !event.isGliding())
			return true;

		IPlayer player = (IPlayer) entity;

		if (Config.canElytraFly(player))
			return true;

		player.sendColouredMessage(Config.Message.elytraFail);
		return false;
	}
}
