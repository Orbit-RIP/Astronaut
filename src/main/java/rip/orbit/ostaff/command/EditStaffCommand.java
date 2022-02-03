package rip.orbit.ostaff.command;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.orbit.ostaff.menu.EditItemMainMenu;
import rip.orbit.ostaff.util.CC;

/**
 * @author LBuddyBoy (lbuddyboy.me)
 * 19/07/2021 / 12:28 AM
 * oStaff / rip.orbit.ostaff.command
 */
public class EditStaffCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("ostaff.staff")) {
			sender.sendMessage(CC.chat("&cNo permission."));
			return false;
		}
		new EditItemMainMenu().openMenu((Player) sender);
		return false;
	}
}
