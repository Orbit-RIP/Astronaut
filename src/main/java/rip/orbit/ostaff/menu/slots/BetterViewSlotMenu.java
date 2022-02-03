package rip.orbit.ostaff.menu.slots;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rip.orbit.ostaff.editor.EditItem;
import rip.orbit.ostaff.menu.EditItemMainMenu;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;
import rip.orbit.ostaff.util.ItemBuilder;
import rip.orbit.ostaff.util.menu.object.Button;
import rip.orbit.ostaff.util.menu.object.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LBuddyBoy (lbuddyboy.me)
 * 19/07/2021 / 12:56 AM
 * oStaff / rip.orbit.ostaff.menu.slots
 */
public class BetterViewSlotMenu extends Menu {
	@Override
	public List<Button> buttons(Player player) {
		List<Button> buttons = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			int finalI = i;
			EditItem loadOut = EditItem.byUUID(player.getUniqueId());
			buttons.add(new Button() {
				@Override
				public ItemStack stack(Player player) {
					if (loadOut.getBetterViewSlot() == (finalI)) {
						return new ItemBuilder(Material.INK_SACK).setData(10).setDisplayName(CC.chat("&aSlot #" + (finalI + 1))).create();
					}
					return new ItemBuilder(Material.INK_SACK).setData(DyeColor.SILVER.getData()).setDisplayName(CC.chat("&7Slot #" + (finalI + 1))).create();
				}

				@Override
				public boolean isUpdateAfterClick(Player player) {
					return true;
				}

				@Override
				public int slot() {
					return (finalI);
				}

				@Override
				public void action(Player player, int slot, InventoryClickEvent event) {
					loadOut.setBetterViewSlot((finalI));
					loadOut.save();
					player.sendMessage(CC.chat("&cSet your better view slot to the #" + (finalI + 1) + " slot."));

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			});
		}

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				return new ItemBuilder(Material.FEATHER).setDisplayName(CC.chat("&bGo Back")).create();
			}

			@Override
			public int slot() {
				return 17;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				new EditItemMainMenu().openMenu(player);
			}
		});

		return buttons;
	}

	@Override
	public int size(Player player) {
		return 18;
	}


	@Override
	public String getTitle(Player player) {
		return "Better View Slot Editor";
	}
}
