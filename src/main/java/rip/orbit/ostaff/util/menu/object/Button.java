package rip.orbit.ostaff.util.menu.object;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

	public abstract ItemStack stack(Player player);
	public abstract int slot();
	private boolean updateAfterClick = false;
	public void action(Player player, int slot, InventoryClickEvent event) {

	}

	public boolean isUpdateAfterClick(Player player) {
		return this.updateAfterClick;
	}


}
