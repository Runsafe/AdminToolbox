package no.runsafe.admintoolbox.kits;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.event.inventory.IInventoryClosed;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.event.inventory.RunsafeInventoryCloseEvent;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.TimeFormatter;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class KitHandler implements IServerReady, IInventoryClosed
{
	public KitHandler(KitRepository repository, KitCooldownRepository cooldownRepository, IServer server)
	{
		this.repository = repository;
		this.cooldownRepository = cooldownRepository;
		this.server = server;
	}

	@Override
	public void OnServerReady()
	{
		kits = repository.getKits();
		cooldownRepository.purgeEndedCooldowns();
		kitCooldowns = cooldownRepository.getKitCooldowns();
	}

	public void deleteKit(String kitName)
	{
		kits.remove(kitName);
		repository.deleteKit(kitName);
		cooldownRepository.removeKitCooldown(kitName);
		kitCooldowns = cooldownRepository.getKitCooldowns();
	}

	public void createKit(String kitName, String universeName, Duration cooldown)
	{
		RunsafeInventory inventory = server.createInventory(null, 36, "Kit: " + kitName);
		KitData kit = new KitData(kitName, inventory, universeName, cooldown);
		kits.put(kitName, kit);
		repository.saveKit(kit);
	}

	public void changeKitCooldown(String kitName, Duration cooldown)
	{
		KitData kit = kits.get(kitName);
		kit.setCooldown(cooldown);
		repository.saveKit(kit);

		if (cooldown.isZero())
		{
			cooldownRepository.removeKitCooldown(kitName);
			kitCooldowns = cooldownRepository.getKitCooldowns();
		}
	}

	public void setKitUniverse(String kitName, String universeName)
	{
		KitData kit = kits.get(kitName);
		kit.setUniverse(universeName);
		repository.saveKit(kit);
	}

	public String getKit(String kitName, IPlayer player)
	{
		if (!kits.containsKey(kitName))
			return Config.Message.Kit.getNotExist;

		// Check universe
		String kitUniverse = kits.get(kitName).getUniverse();
		if (kitUniverse != null && player.getUniverse() != null
			&& !player.hasPermission("runsafe.toolbox.kits.ignoreuniverse")
			&& !player.getUniverse().getName().equals(kitUniverse)
		)
			return String.format(Config.Message.Kit.getWrongWorld, kitUniverse);

		// Check if they have enough inventory space
		int requiredEmptySpaces = kits.get(kitName).getInventory().getContents().size();
		int emptyInventorySpaces = 36 - player.getInventory().getContents().size();

		if (emptyInventorySpaces < requiredEmptySpaces)
			return String.format(Config.Message.Kit.getInventoryFull, requiredEmptySpaces);

		// Check cooldown
		if (!player.hasPermission("runsafe.toolbox.kits.cooldownbypass") && !kits.get(kitName).getCooldown().isZero())
		{
			if (kitCooldowns.containsKey(player) && kitCooldowns.get(player).containsKey(kitName))
			{
				if (!kitCooldowns.get(player).get(kitName).isBefore(Instant.now()))
					return String.format(
						Config.Message.Kit.getOnCooldown,
						TimeFormatter.formatInstant(kitCooldowns.get(player).get(kitName))
					);

				kitCooldowns.get(player).remove(kitName);
			}

			if (!kitCooldowns.containsKey(player))
				kitCooldowns.put(player, new HashMap<>(0));

			Instant cooldownEnd = Instant.now().plus(kits.get(kitName).getCooldown());
			cooldownRepository.setCooldown(player, kitName, cooldownEnd);
			kitCooldowns.get(player).put(kitName, cooldownEnd);
		}

		giveKit(kitName, player);

		return String.format(Config.Message.Kit.getSucceed, kitName);
	}

	public void giveKit(String kitName, IPlayer player)
	{
		RunsafeInventory playerInventory = player.getInventory();
		for (RunsafeMeta item : kits.get(kitName).getInventory().getContents())
			if (playerInventory.getContents().size() < playerInventory.getSize())
				player.give(item);
			else
				player.getWorld().dropItem(player.getLocation(), item);
	}

	public KitData getKitData(String kitName)
	{
		return kits.get(kitName);
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

	public String editKitInventory(IPlayer player, String kitName)
	{
		if (inventoryEdits.containsKey(player) || inventoryEdits.containsValue(kitName))
			return String.format(Config.Message.Kit.inventoryEditFailConcurrent, kitName);

		player.openInventory(kits.get(kitName).getInventory());
		inventoryEdits.put(player, kitName);
		return null;
	}

	@Override
	public void OnInventoryClosed(RunsafeInventoryCloseEvent event)
	{
		IPlayer player = event.getPlayer();
		if (!inventoryEdits.containsKey(player))
			return;

		String kitName = inventoryEdits.get(player);
		inventoryEdits.remove(player);

		KitData kit = kits.get(kitName);
		if (kit == null)
		{
			player.sendColouredMessage(Config.Message.Kit.inventoryEditFailNonExistent, kitName);
			return;
		}

		RunsafeInventory newInventory = event.getInventory();
		kit.setInventory(newInventory);
		repository.saveKit(kit);

		player.sendColouredMessage(Config.Message.Kit.inventoryEditSucceed, kitName);
	}

	private final KitRepository repository;
	private final KitCooldownRepository cooldownRepository;
	private final IServer server;
	private HashMap<String, KitData> kits = new HashMap<>(0);
	private HashMap<IPlayer, HashMap<String, Instant>> kitCooldowns = new HashMap<>(0);
	private final HashMap<IPlayer, String> inventoryEdits = new HashMap<>(0);
}
