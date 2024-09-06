package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

import java.time.Duration;

public class KitData
{
	public KitData(String kitName, RunsafeInventory inventory, Duration cooldown)
	{
		this.kitName = kitName;
		this.inventory = inventory;
		this.cooldown = cooldown;
		if (cooldown == null)
			this.cooldown = Duration.ZERO;
	}

	public String getKitName()
	{
		return kitName;
	}

	public void setKitName(String kitName)
	{
		this.kitName = kitName;
	}

	public RunsafeInventory getInventory()
	{
		return inventory;
	}

	public void setInventory(RunsafeInventory inventory)
	{
		this.inventory = inventory;
	}

	public Duration getCooldown()
	{
		return cooldown;
	}

	public void setCooldown(Duration cooldown)
	{
		this.cooldown = cooldown;
		if (cooldown == null)
			this.cooldown = Duration.ZERO;
	}

	private String kitName;
	private RunsafeInventory inventory;
	private Duration cooldown;
}
