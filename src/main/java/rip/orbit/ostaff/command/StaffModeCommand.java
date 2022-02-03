package rip.orbit.ostaff.command;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;

public class StaffModeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("ostaff.staff")) {
			sender.sendMessage(CC.chat("&cNo permission."));
			return false;
		}
		if (args.length > 0) {
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(CC.chat("&cCould not locate that player."));
				return false;
			}
			if (oStaff.getInstance().getStaffModeHandler().inStaffMode(target)) {
				oStaff.getInstance().getStaffModeHandler().unloadStaffMode(target);
				sender.sendMessage(CC.chat("&fYou have just &cdisabled&f " + target.getName() + " mod mode."));
			} else {
				oStaff.getInstance().getStaffModeHandler().loadStaffMode(target);
				sender.sendMessage(CC.chat("&fYou have just &6enabled&f " + target.getName() + " mod mode."));
			}
			return false;
		}
		if (oStaff.getInstance().getStaffModeHandler().inStaffMode((Player) sender)) {
			oStaff.getInstance().getStaffModeHandler().unloadStaffMode((Player) sender);
		} else {
			oStaff.getInstance().getStaffModeHandler().loadStaffMode((Player) sender);
			sender.sendMessage(CC.chat("&fYou have just &6enabled&f your mod mode."));
			sender.sendMessage(CC.chat("&7&oYou can edit your staff mode item's slots & availability by running /editstaff"));
		}
		return false;
	}
}
