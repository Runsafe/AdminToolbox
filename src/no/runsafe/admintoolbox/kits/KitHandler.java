package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.HashMap;

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

	public boolean kitExists(String kitName)
	{
		return kits.containsKey(kitName);
	}

	private final KitRepository repository;
	private final IServer server;
	private HashMap<String, RunsafeInventory> kits = new HashMap<String, RunsafeInventory>(0);
}
