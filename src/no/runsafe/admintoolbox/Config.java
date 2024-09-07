package no.runsafe.admintoolbox;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Config implements IConfigurationChanged
{
	@Override
	public void OnConfigurationChanged(IConfiguration config)
	{
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
	}

	public static final class Message
	{
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
	}
}
