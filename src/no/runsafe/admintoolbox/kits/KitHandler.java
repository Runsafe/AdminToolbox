package no.runsafe.admintoolbox.kits;

import no.runsafe.admintoolbox.Config;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.TimeFormatter;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitHandler implements IServerReady
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

	public void createKit(String kitName, IPlayer player, String universeName, Duration cooldown)
	{
		RunsafeInventory inventory = server.createInventory(null, 36);
		inventory.unserialize(player.getInventory().serialize());
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
		for (RunsafeMeta item : kits.get(kitName).getInventory().getContents())
			player.give(item);
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

	private final KitRepository repository;
	private final KitCooldownRepository cooldownRepository;
	private final IServer server;
	private HashMap<String, KitData> kits = new HashMap<>(0);
	private HashMap<IPlayer, HashMap<String, Instant>> kitCooldowns = new HashMap<>(0);
}
