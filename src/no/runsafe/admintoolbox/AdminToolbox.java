package no.runsafe.admintoolbox;

import no.runsafe.admintoolbox.binding.BindingHandler;
import no.runsafe.admintoolbox.commands.*;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Events;

public class AdminToolbox extends RunsafePlugin
{
	public static IDebug Debugger = null;

	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);
		addComponent(Events.class);

		Debugger = getComponent(IDebug.class);

		addComponent(BindingHandler.class);

		addComponent(Author.class);
		addComponent(Colour.class);
		addComponent(GiveItem.class);
		addComponent(RenameItem.class);
		addComponent(Mode.class);
		addComponent(Bind.class);
		addComponent(BindPermanent.class);
		addComponent(AddLore.class);
		addComponent(Repair.class);
	}
}
