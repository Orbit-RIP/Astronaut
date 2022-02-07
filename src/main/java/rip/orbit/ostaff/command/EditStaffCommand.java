package rip.orbit.ostaff.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.orbit.ostaff.menu.EditItemMainMenu;
import rip.orbit.ostaff.util.CC;

public class EditStaffCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("orbit.staff")) {
			sender.sendMessage(CC.chat("&cNo permission."));
			return false;
		}
		new EditItemMainMenu().openMenu((Player) sender);
		return false;
	}
}
