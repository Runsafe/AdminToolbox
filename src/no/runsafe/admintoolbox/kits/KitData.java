package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

import java.time.Duration;

public class KitData
{
	public KitData(String kitName, RunsafeInventory inventory, String universeName, Duration cooldown)
	{
		this.kitName = kitName;
		this.inventory = inventory;
		this.universeName = universeName;
		this.cooldown = cooldown;
		if (cooldown == null)
			this.cooldown = Duration.ZERO;
	}

	public String getKitName()
	{
		return kitName;
	}

	public RunsafeInventory getInventory()
	{
		return inventory;
	}

	public void setInventory(RunsafeInventory inventory)
	{
		this.inventory = inventory;
	}

	public String getUniverse()
	{
		return universeName;
	}

	public void setUniverse(String universeName)
	{
		this.universeName = universeName;
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

	private final String kitName;
	private RunsafeInventory inventory;
	private String universeName;
	private Duration cooldown;
}
