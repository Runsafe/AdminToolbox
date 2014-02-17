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

	public void saveKit(String name, RunsafeInventory kit)
	{
		String inventory = kit.serialize();
		database.execute(
				"INSERT INTO `toolbox_kits` (`ID`, `inventory`) VALUES(?, ?) ON DUPLICATE KEY UPDATE `inventory` = ?",
				name, inventory, inventory
		);
	}

	public HashMap<String, RunsafeInventory> getKits()
	{
		HashMap<String, RunsafeInventory> kits = new HashMap<String, RunsafeInventory>(0);

		for (IRow row : database.query("SELECT `ID`, `inventory` FROM `toolbox_kits`"))
		{
			RunsafeInventory inventory = server.createInventory(null, 36);
			inventory.unserialize(row.String("inventory"));
			kits.put(row.String("ID"), inventory);
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

		return updates;
	}

	private final IServer server;
}
