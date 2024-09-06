package no.runsafe.admintoolbox.kits;

import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.database.*;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

import javax.annotation.Nonnull;
import java.util.HashMap;

public class KitRepository extends Repository
{
	public KitRepository(IDatabase database, IServer server)
	{
		this.server = server;
		this.database = database;
	}

	@Nonnull
	@Override
	public String getTableName()
	{
		return "toolbox_kits";
	}

	public void deleteKit(String name)
	{
		database.execute("DELETE FROM `toolbox_kits` WHERE `ID` = ?", name);
	}

	public void saveKit(KitData kit)
	{
		database.execute(
			"INSERT INTO `toolbox_kits` (`ID`, `inventory`, `cooldownTime`) VALUES(?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE `inventory` = VALUES(`inventory`), `cooldownTime` = VALUES(`cooldownTime`)",
			kit.getKitName(), kit.getInventory().serialize(), kit.getCooldown()
		);
	}

	public HashMap<String, KitData> getKits()
	{
		HashMap<String, KitData> kits = new HashMap<>(0);

		for (IRow row : database.query("SELECT `ID`, `inventory`, `cooldownTime` FROM `toolbox_kits`"))
		{
			RunsafeInventory inventory = server.createInventory(null, 36);
			inventory.unserialize(row.String("inventory"));
			String kitName = row.String("ID");
			kits.put(kitName, new KitData(kitName, inventory, row.Duration("cooldownTime")));
		}

		return kits;
	}

	@Nonnull
	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate updates = new SchemaUpdate();

		updates.addQueries(
			"CREATE TABLE `toolbox_kits` (" +
				"`ID` VARCHAR(20) NOT NULL," +
				"`inventory` LONGTEXT NOT NULL," +
				"PRIMARY KEY (`ID`)" +
			")"
		);

		updates.addQueries(
			"ALTER TABLE `toolbox_kits` ADD COLUMN `cooldownTime` VARCHAR(32) NULL DEFAULT NULL AFTER `inventory`;"
		);

		return updates;
	}

	private final IServer server;
}
