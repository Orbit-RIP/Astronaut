package rip.orbit.ostaff;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import rip.orbit.ostaff.command.EditStaffCommand;
import rip.orbit.ostaff.command.StaffModeCommand;
import rip.orbit.ostaff.command.ToggleStaffCommand;
import rip.orbit.ostaff.command.VanishCommand;
import rip.orbit.ostaff.util.menu.MenuManager;

public final class oStaff extends JavaPlugin {

	@Getter private static oStaff instance;
	@Getter private StaffModeHandler staffModeHandler;
	@Getter private MongoDatabase mongoDatabase;

	@Override
	public void onEnable() {
		instance = this;

		this.saveDefaultConfig();

		new MenuManager();

		staffModeHandler = new StaffModeHandler(this);
		getCommand("togglestaff").setExecutor(new ToggleStaffCommand());
		getCommand("staffmode").setExecutor(new StaffModeCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("editstaff").setExecutor(new EditStaffCommand());

		loadMongo();
	}

	@Override
	public void onDisable() {
		instance = null;
	}

	private void loadMongo() {
		if (getConfig().getBoolean("MONGO.AUTHENTICATION.ENABLED")) {
			mongoDatabase = new MongoClient(
					new ServerAddress(
							getConfig().getString("MONGO.HOST"),
							getConfig().getInt("MONGO.PORT")),
					MongoCredential.createCredential(
							getConfig().getString("MONGO.AUTHENTICATION.USERNAME"),
							"admin", getConfig().getString("MONGO.AUTHENTICATION.PASSWORD").toCharArray()),
					MongoClientOptions.builder().build()
			).getDatabase("oStaff");
		} else {
			mongoDatabase = new MongoClient(getConfig().getString("MONGO.HOST"), getConfig().getInt("MONGO.PORT"))
					.getDatabase("oStaff");
		}
	}
}
