package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class InventoryEdit extends PlayerCommand
{
	public InventoryEdit(KitHandler handler)
	{
		super(
				"inventoryedit",
				"Change a kit's inventory",
				"runsafe.toolbox.kits.inventoryedit",
				new KitArgument(handler)
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		return handler.editKitInventory(executor, parameters.getRequired("kit"));
	}

	private final KitHandler handler;
}
