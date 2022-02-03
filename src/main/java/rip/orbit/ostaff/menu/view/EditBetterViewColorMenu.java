package rip.orbit.ostaff.menu.view;

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
 * 19/07/2021 / 2:14 AM
 * oStaff / rip.orbit.ostaff.menu.view
 */
public class EditBetterViewColorMenu extends Menu {
	@Override
	public List<Button> buttons(Player player) {
		List<Button> buttons = new ArrayList<>();

		for (int i = 0; i < 15; i++) {
			int finalI = i;
			EditItem loadOut = EditItem.byUUID(player.getUniqueId());
			int finalI1 = i;
			buttons.add(new Button() {
				@Override
				public ItemStack stack(Player player) {
					if (loadOut.getBetterViewData() == (finalI)) {
						return new ItemBuilder(Material.INK_SACK).setData(10).setDisplayName(CC.chat("&aBetter View #" + (finalI + 1))).create();
					}
					return new ItemBuilder(Material.CARPET).setData(finalI1).setDisplayName(CC.chat("&7Better View #" + (finalI + 1))).create();
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
					loadOut.setBetterViewData((finalI));
					loadOut.save();
					player.sendMessage(CC.chat("&cSet your carpet color to the #" + (finalI + 1) + " colored carpet."));

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
				return 26;
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
		return 27;
	}


	@Override
	public String getTitle(Player player) {
		return "Better View Color Editor";
	}
}

