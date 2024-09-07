package no.runsafe.admintoolbox;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
		Message.playerNotHoldingItem = config.getConfigValueAsString("message.playerNotHoldingItem");

		Message.Kit.invalidName = config.getConfigValueAsString("message.kit.invalidName");
		Message.Kit.created = config.getConfigValueAsString("message.kit.created");
		Message.Kit.created2 = config.getConfigValueAsString("message.kit.created2");
		Message.Kit.deleted = config.getConfigValueAsString("message.kit.deleted");
		Message.Kit.give = config.getConfigValueAsString("message.kit.give");
		Message.Kit.noneAvailable = config.getConfigValueAsString("message.kit.noneAvailable");
		Message.Kit.available = config.getConfigValueAsString("message.kit.available");
		Message.Kit.availableSeparator = config.getConfigValueAsString("message.kit.availableSeparator");
		Message.Kit.removeCooldownTime = config.getConfigValueAsString("message.kit.removeCooldownTime");
		Message.Kit.setCooldownTime = config.getConfigValueAsString("message.kit.setCooldownTime");
		Message.Kit.removeUniverse = config.getConfigValueAsString("message.kit.removeUniverse");
		Message.Kit.setUniverse = config.getConfigValueAsString("message.kit.setUniverse");
		Message.Kit.getNotExist = config.getConfigValueAsString("message.kit.getNotExist");
		Message.Kit.getWrongWorld = config.getConfigValueAsString("message.kit.getWrongWorld");
		Message.Kit.getOnCooldown = config.getConfigValueAsString("message.kit.getOnCooldown");
		Message.Kit.getSucceed = config.getConfigValueAsString("message.kit.getSucceed");

		Message.lore.addSucceed = config.getConfigValueAsString("message.lore.addSucceed");
		Message.lore.removeSucceed = config.getConfigValueAsString("message.lore.removeSucceed");

		Message.author.setFail = config.getConfigValueAsString("message.author.setFail");
		Message.author.setSucceed = config.getConfigValueAsString("message.author.setSucceed");

		Message.bind.setUnbind = config.getConfigValueAsString("message.bind.setUnbind");
		Message.bind.setSucceed = config.getConfigValueAsString("message.bind.setSucceed");

		Message.colour.setFail = config.getConfigValueAsString("message.colour.setFail");
		Message.colour.setSucceed = config.getConfigValueAsString("message.colour.setSucceed");

		Message.give.failInvalidItem = config.getConfigValueAsString("message.give.failInvalidItem");
		Message.give.failAir = config.getConfigValueAsString("message.give.failAir");
		Message.give.succeed = config.getConfigValueAsString("message.give.succeed");

		Message.mode.failConsole = config.getConfigValueAsString("message.mode.failConsole");
		Message.mode.succeed = config.getConfigValueAsString("message.mode.succeed");

		Message.renameItem.fail = config.getConfigValueAsString("message.renameItem.fail");
		Message.renameItem.succeed = config.getConfigValueAsString("message.renameItem.succeed");

		Message.repair.succeed = config.getConfigValueAsString("message.repair.succeed");
	}

	public static final class Message
	{
		public static String playerNotHoldingItem;

		public static final class Kit
		{
			public static String invalidName;
			public static String created;
			public static String created2;
			public static String deleted;
			public static String give;
			public static String noneAvailable;
			public static String available;
			public static String availableSeparator;
			public static String removeCooldownTime;
			public static String setCooldownTime;
			public static String removeUniverse;
			public static String setUniverse;
			public static String getNotExist;
			public static String getWrongWorld;
			public static String getOnCooldown;
			public static String getSucceed;
		}

		public static final class lore
		{
			public static String addSucceed;
			public static String removeSucceed;
		}

		public static final class author
		{
			public static String setFail;
			public static String setSucceed;
		}

		public static final class bind
		{
			public static String setUnbind;
			public static String setSucceed;
		}

		public static final class colour
		{
			public static String setFail;
			public static String setSucceed;
		}

		public static final class give
		{
			public static String failInvalidItem;
			public static String failAir;
			public static String succeed;
		}

		public static final class mode
		{
			public static String failConsole;
			public static String succeed;
		}

		public static final class renameItem
		{
			public static String fail;
			public static String succeed;
		}

		public static final class repair
		{
			public static String succeed;
		}
	}
}
