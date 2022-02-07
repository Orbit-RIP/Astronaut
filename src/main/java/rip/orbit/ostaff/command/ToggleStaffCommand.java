package rip.orbit.ostaff.command;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;

public class ToggleStaffCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("orbit.staff")) {
			sender.sendMessage(CC.chat("&cNo permission."));
			return false;
		}
		if (((Player) sender).hasMetadata("hidestaff")){
			sender.sendMessage(ChatColor.GREEN + "Successfully shown staff!");
			((Player) sender).removeMetadata("hidestaff", oStaff.getInstance());
			for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()){
				// cant stack
				if (otherPlayer != sender){
					if (otherPlayer.hasMetadata("modmode")){
						((Player) sender).showPlayer(otherPlayer);
					}
				}
			}
		} else {
			((Player) sender).setMetadata("hidestaff", new FixedMetadataValue(oStaff.getInstance(), true));
			sender.sendMessage(ChatColor.GREEN + "Successfully hidden staff!");
			for (Player otherPlayer : Bukkit.getServer().getOnlinePlayers()){
				// cant stack them
				if (otherPlayer != sender){
					if (otherPlayer.hasMetadata("modmode")){
						((Player) sender).hidePlayer(otherPlayer);
					}
				}
			}
		}
		return false;
	}
}
