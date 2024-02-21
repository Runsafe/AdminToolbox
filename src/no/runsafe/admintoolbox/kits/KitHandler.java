package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitHandler implements IServerReady
{
	public KitHandler(KitRepository repository, IServer server)
	{
		this.repository = repository;
		this.server = server;
	}

	@Override
	public void OnServerReady()
	{
		kits = repository.getKits();
	}

	public void deleteKit(String kitName)
	{
		kits.remove(kitName);
		repository.deleteKit(kitName);
	}

	public void createKit(String kitName, IPlayer player)
	{
		RunsafeInventory inventory = server.createInventory(null, 36);
		inventory.unserialize(player.getInventory().serialize());
		kits.put(kitName, inventory);
		repository.saveKit(kitName, inventory);
	}

	public void getKit(String kitName, IPlayer player)
	{
		if (kits.containsKey(kitName))
			for (RunsafeMeta item : kits.get(kitName).getContents())
				player.give(item);
	}

	public boolean isInvalidKit(String kitName)
	{
		return !kits.containsKey(kitName);
	}

	public List<String> getAvailableKits(IPlayer player)
	{
		List<String> availableKits = new ArrayList<>(0);
		for (String kit : kits.keySet())
			if (canUseKit(player, kit))
				availableKits.add(kit);

		return availableKits;
	}

	public boolean canUseKit(IPlayer player, String kitName)
	{
		return player == null || player.hasPermission("runsafe.toolbox.kits.get." + kitName);
	}

	private final KitRepository repository;
	private final IServer server;
	private HashMap<String, RunsafeInventory> kits = new HashMap<>(0);
}
