package rip.orbit.ostaff.command;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;

public class VanishCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("ostaff.vanish")) {
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
				oStaff.getInstance().getStaffModeHandler().unloadVanish((Player) sender);
				sender.sendMessage(CC.chat("&fYou have just &cdisabled&f " + target.getName() + " vanish."));
			} else {
				oStaff.getInstance().getStaffModeHandler().loadVanish((Player) sender);
				sender.sendMessage(CC.chat("&fYou have just &6enabled&f " + target.getName() + " vanish."));
			}
			return false;
		}
		if (oStaff.getInstance().getStaffModeHandler().isVanished((Player) sender)) {
			oStaff.getInstance().getStaffModeHandler().unloadVanish((Player) sender);
			sender.sendMessage(CC.chat("&eVanish: &cDisabled"));
		} else {
			oStaff.getInstance().getStaffModeHandler().loadVanish((Player) sender);
			sender.sendMessage(CC.chat("&eVanish: &aEnabled"));
		}
		return false;
	}
}