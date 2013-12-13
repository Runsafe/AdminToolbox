package no.runsafe.admintoolbox;

import no.runsafe.admintoolbox.commands.*;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.log.IDebug;

public class Plugin extends RunsafePlugin
{
	public static IDebug Debugger = null;

	@Override
	protected void PluginSetup()
	{
		Debugger = getComponent(IDebug.class);

		addComponent(Author.class);
		addComponent(Colour.class);
		addComponent(GiveItem.class);
		addComponent(RenameItem.class);
		addComponent(Mode.class);
	}
}
