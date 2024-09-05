package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.time.Duration;
import java.time.Instant;
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

	public void createKit(String kitName, IPlayer player, Duration cooldown)
	{
		RunsafeInventory inventory = server.createInventory(null, 36);
		inventory.unserialize(player.getInventory().serialize());
		KitData kit = new KitData(kitName, inventory, cooldown);
		kits.put(kitName, kit);
		repository.saveKit(kit);
	}

	public void changeKitCooldown(String kitName, Duration cooldown)
	{
		KitData kit = kits.get(kitName);
		kit.setCooldown(cooldown);
		repository.saveKit(kit);
	}

	public String getKit(String kitName, IPlayer player)
	{
		if (!kits.containsKey(kitName))
			return "&cThat kit does not exist.";

		if (!player.hasPermission("runsafe.toolbox.kits.cooldownbypass")
			|| kits.get(kitName).getCooldown() == null
			|| kits.get(kitName).getCooldown().isZero()
		)
		{
			if (kitCooldowns.containsKey(player) && kitCooldowns.get(player).containsKey(kitName))
			{
				if (!kitCooldowns.get(player).get(kitName).isBefore(Instant.now()))
					return String.format(
							"&cStill on cooldown. Time until you can redeem this kit&r: %s",
							Duration.between(Instant.now(), kitCooldowns.get(player).get(kitName)).toString().replace("PT", "")
					);

				kitCooldowns.get(player).remove(kitName);
			}

			if (!kitCooldowns.containsKey(player))
				kitCooldowns.put(player, new HashMap<>(0));

			kitCooldowns.get(player).put(kitName, Instant.now().plus(kits.get(kitName).getCooldown()));
		}

		for (RunsafeMeta item : kits.get(kitName).getInventory().getContents())
			player.give(item);

		return String.format("&aObtained kit&r: %s", kitName);
	}

	public List<String> getAvailableKits(ICommandExecutor player)
	{
		List<String> availableKits = new ArrayList<>(0);
		for (String kit : kits.keySet())
			if (!(player instanceof IPlayer) || canUseKit((IPlayer) player, kit))
				availableKits.add(kit);

		return availableKits;
	}

	public boolean canUseKit(IPlayer player, String kitName)
	{
		return player == null || player.hasPermission("runsafe.toolbox.kits.get." + kitName);
	}

	private final KitRepository repository;
	private final IServer server;
	private HashMap<String, KitData> kits = new HashMap<>(0);
	private final HashMap<IPlayer, HashMap<String, Instant>> kitCooldowns = new HashMap<>(0);
}
