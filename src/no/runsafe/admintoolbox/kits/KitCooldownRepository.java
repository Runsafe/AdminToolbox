package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.ISchemaUpdate;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.api.database.SchemaUpdate;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.HashMap;

public class KitCooldownRepository extends Repository
{
	@Nonnull
	@Override
	public String getTableName()
	{
		return "toolbox_kitcooldowns";
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate updates = new SchemaUpdate();

		updates.addQueries(
			"CREATE TABLE `toolbox_kitcooldowns` (" +
				"`player` VARCHAR(36) NOT NULL, " +
				"`kit_name` VARCHAR(20) NOT NULL, " +
				"`cooldown_end` datetime NOT NULL, " +
				"PRIMARY KEY (`player`, `kit_name`)" +
			");"
		);
		return updates;
	}

	public void removeKitCooldown(String kitName)
	{
		database.execute("DELETE FROM `toolbox_kitcooldowns` WHERE `kit_name` = ?;", kitName);
	}

	public HashMap<IPlayer, HashMap<String, Instant>> getKitCooldowns()
	{
		HashMap<IPlayer, HashMap<String, Instant>> kitCooldowns = new HashMap<>(0);

		for (IRow row : database.query("SELECT `player`, `kit_name`, `cooldown_end` FROM `toolbox_kits`"))
		{
			IPlayer player = row.Player("player");
			String kitName = row.String("kit_name");
			Instant cooldownEnd = row.Instant("cooldown_end");

			if (!kitCooldowns.containsKey(player))
				kitCooldowns.put(player, new HashMap<>(0));

			kitCooldowns.get(player).put(kitName, cooldownEnd);
		}

		return kitCooldowns;
	}

	public void setCooldown(IPlayer player, String kitName, Instant cooldownEnd)
	{
		database.execute(
			"INSERT INTO `toolbox_kitcooldowns` (`player`, `kit_name`, `cooldown_end`) VALUES(?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE `cooldown_end` = VALUES(`cooldown_end`);",
			player, kitName, cooldownEnd
		);
	}

	public void purgeEndedCooldowns()
	{
		database.execute("DELETE FROM `toolbox_kitcooldowns` WHERE `cooldown_end` < NOW();");
	}
}
