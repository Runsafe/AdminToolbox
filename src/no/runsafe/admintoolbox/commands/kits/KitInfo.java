package no.runsafe.admintoolbox.commands.kits;

import no.runsafe.admintoolbox.kits.KitData;
import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.framework.api.command.ExecutableCommand;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.TimeFormatter;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class KitInfo extends ExecutableCommand
{
	public KitInfo(KitHandler handler)
	{
		super(
			"info",
			"Lists information on a kit",
			"runsafe.toolbox.kits.info",
			new KitArgument(handler)
		);
		this.handler = handler;
	}

	@Override
	public String OnExecute(ICommandExecutor executor, IArgumentList parameters)
	{
		String kitName = parameters.getValue("kit");
		KitData kit = handler.getKitData(kitName);

		ArrayList<String> itemList = new ArrayList<>();
		for (RunsafeMeta item : kit.getInventory().getContents())
			itemList.add(item.getDisplayName() == null ? item.getNormalName() : item.getDisplayName());

		return "&5KitName: &r" + kitName +
			", \n&9Universe you can redeem kit in: &r" + (kit.getUniverse() == null ? "Everywhere" : kit.getUniverse()) +
			", \n&9Kit cooldown time: &r" + TimeFormatter.formatDuration(kit.getCooldown()) +
			", \n&9Kit Items: &r" + StringUtils.join(itemList, ", ");
	}

	private final KitHandler handler;
}
