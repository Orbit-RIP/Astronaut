package rip.orbit.ostaff.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/*
* Made By LBuddyBoy
* */
public class ItemUtils {

	public static boolean hasLore(ItemStack stack) {
		return stack != null && stack.getItemMeta() != null && stack.getItemMeta().getLore() != null && !stack.getItemMeta().getLore().isEmpty();
	}

	public static boolean hasDisplayName(ItemStack stack) {
		return stack != null && stack.getItemMeta() != null && stack.getItemMeta().getDisplayName() != null && !stack.getItemMeta().getDisplayName().isEmpty();
	}


	public static void tryFit(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		boolean canfit = false;
		for (int i = 0; i < inv.getSize(); ++i) {
			if (inv.getItem(i) == null || inv.getItem(i) != null && inv.getItem(i).getType() == Material.AIR) {
				canfit = true;
				inv.addItem(item);
				break;
			}
		}
		if (!canfit) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		}
	}

}
