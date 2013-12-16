package no.runsafe.admintoolbox;

import no.runsafe.admintoolbox.commands.*;
import no.runsafe.admintoolbox.handlers.Hooks;
import no.runsafe.admintoolbox.handlers.VanishHandler;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.FrameworkHooks;

public class Plugin extends RunsafePlugin
{
	public static IDebug Debugger = null;

	@Override
	protected void pluginSetup()
	{
		addComponent(FrameworkHooks.class);
		addComponent(Commands.class);

		Debugger = getComponent(IDebug.class);

		addComponent(Hooks.class);
		addComponent(VanishHandler.class);

		addComponent(Author.class);
		addComponent(Colour.class);
		addComponent(GiveItem.class);
		addComponent(RenameItem.class);
		addComponent(Mode.class);
		addComponent(Vanish.class);
	}
}
