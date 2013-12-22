package no.runsafe.admintoolbox;

import no.runsafe.admintoolbox.commands.*;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.FrameworkHooks;

public class AdminToolbox extends RunsafePlugin
{
	public static IDebug Debugger = null;

	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);

		Debugger = getComponent(IDebug.class);

		addComponent(Author.class);
		addComponent(Colour.class);
		addComponent(GiveItem.class);
		addComponent(RenameItem.class);
		addComponent(Mode.class);
	}
}
