package rip.orbit.ostaff;

import cc.fyre.proton.Proton;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import rip.orbit.ostaff.command.EditStaffCommand;
import rip.orbit.ostaff.command.StaffModeCommand;
import rip.orbit.ostaff.command.ToggleStaffCommand;
import rip.orbit.ostaff.command.VanishCommand;
import rip.orbit.ostaff.util.MongoHandler;
import rip.orbit.ostaff.util.menu.MenuManager;

public final class oStaff extends JavaPlugin {

	@Getter private static oStaff instance;
	@Getter private StaffModeHandler staffModeHandler;

	@Getter private MongoHandler mongoHandler;

	@Override
	public void onEnable() {
		instance = this;

		this.saveDefaultConfig();

		new MenuManager();
		Proton.getInstance().getCommandHandler().registerAll(this);

		staffModeHandler = new StaffModeHandler(this);
		getCommand("togglestaff").setExecutor(new ToggleStaffCommand());
		getCommand("staffmode").setExecutor(new StaffModeCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("editstaff").setExecutor(new EditStaffCommand());

		mongoHandler = new MongoHandler(this);
	}

	@Override
	public void onDisable() {
		instance = null;
	}

}
