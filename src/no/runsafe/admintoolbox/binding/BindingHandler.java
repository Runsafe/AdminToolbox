package no.runsafe.admintoolbox.binding;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class BindingHandler implements IPlayerRightClick
{
	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		if (usingItem != null)
		{
			NBTTagCompound tag = usingItem.getTagCompound();
			if (tag != null && tag.hasKey("runsafe.bound-commands"))
			{
				new CommandBinding(
					usingItem.getTagCompound().getString("runsafe.bound-commands")
				).execute(player);
			}
			else
			{
				CommandBinding binding = getBinding(player, usingItem.getItemType());
				if (binding != null)
					binding.execute(player);
			}
		}
		return true;
	}

	public void removeBinding(IPlayer player, Item item)
	{
		UUID playerUUID = player.getUniqueId();
		if (bindings.containsKey(playerUUID))
			bindings.get(playerUUID).remove(item);
	}

	public void addBinding(IPlayer player, Item item, String... commands)
	{
		UUID playerUUID = player.getUniqueId();
		if (!bindings.containsKey(playerUUID))
			bindings.put(playerUUID, new ConcurrentHashMap<Item, CommandBinding>(1));

		bindings.get(playerUUID).put(item, new CommandBinding(commands));
	}

	public CommandBinding getBinding(IPlayer player, Item item)
	{
		UUID playerUUID = player.getUniqueId();

		if (bindings.containsKey(playerUUID))
		{
			ConcurrentHashMap<Item, CommandBinding> playerBindings = bindings.get(playerUUID);
			for (Map.Entry<Item, CommandBinding> node : playerBindings.entrySet())
				if (node.getKey().equals(item))
					return node.getValue();
		}

		return null;
	}

	private final ConcurrentHashMap<UUID, ConcurrentHashMap<Item, CommandBinding>> bindings = new ConcurrentHashMap<UUID, ConcurrentHashMap<Item, CommandBinding>>(0);
}
