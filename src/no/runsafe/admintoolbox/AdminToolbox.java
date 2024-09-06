package no.runsafe.admintoolbox;

import no.runsafe.admintoolbox.binding.BindingHandler;
import no.runsafe.admintoolbox.commands.*;
import no.runsafe.admintoolbox.commands.kits.*;
import no.runsafe.admintoolbox.kits.KitHandler;
import no.runsafe.admintoolbox.kits.KitRepository;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.log.IDebug;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;

public class AdminToolbox extends RunsafePlugin
{
	public static IDebug Debugger = null;

	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);
		addComponent(Events.class);
		addComponent(Database.class);

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
		addComponent(ClearLore.class);
		addComponent(Repair.class);

		addComponent(KitRepository.class);
		addComponent(KitHandler.class);
		Command kitCommand = new Command("kit", "Kit related commands", null);
		addComponent(kitCommand);

		kitCommand.addSubCommand(getInstance(GetKit.class));
		kitCommand.addSubCommand(getInstance(CreateKit.class));
		kitCommand.addSubCommand(getInstance(DeleteKit.class));
		kitCommand.addSubCommand(getInstance(GiveKit.class));
		kitCommand.addSubCommand(getInstance(ListKits.class));
		kitCommand.addSubCommand(getInstance(SetCooldownTime.class));
		kitCommand.addSubCommand(getInstance(KitInfo.class));
	}
}
