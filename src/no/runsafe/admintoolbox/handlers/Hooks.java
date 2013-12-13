package no.runsafe.admintoolbox.handlers;

import no.runsafe.framework.api.hook.IPlayerDataProvider;
import no.runsafe.framework.api.hook.IPlayerVisibility;
import no.runsafe.framework.api.player.IPlayer;

import java.util.HashMap;
import java.util.Map;

public class Hooks implements IPlayerDataProvider, IPlayerVisibility
{
	public Hooks(VanishHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public Map<String, String> GetPlayerData(IPlayer player)
	{
		if (handler.isHidden(player))
		{
			HashMap<String, String> response = new HashMap<String, String>(1);
			response.put("vanished", "true");
			return response;
		}
		return null;
	}

	@Override
	public boolean isPlayerHidden(IPlayer viewer, IPlayer target)
	{
		return handler.isHidden(target, viewer);
	}

	@Override
	public boolean isPlayerVanished(IPlayer player)
	{
		return handler.isHidden(player);
	}

	private final VanishHandler handler;
}
