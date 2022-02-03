package rip.orbit.ostaff.util.menu;

import org.bukkit.Bukkit;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.menu.listener.MenuListener;


public class MenuManager {

	public MenuManager() {
		Bukkit.getPluginManager().registerEvents(new MenuListener(), oStaff.getInstance());
	}

}
