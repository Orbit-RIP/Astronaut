package rip.orbit.ostaff.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class CC {

	public static String chat(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}


	public static List<String> chat(List<String> lore) {
		List<String> lines = new ArrayList<>();
		for (String s : lore) {
			lines.add(ChatColor.translateAlternateColorCodes('&', s));
		}
		return lines;
	}

}
