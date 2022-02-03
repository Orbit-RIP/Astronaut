package rip.orbit.ostaff.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import rip.orbit.ostaff.editor.EditItem;
import rip.orbit.ostaff.menu.slots.*;
import rip.orbit.ostaff.menu.view.EditBetterViewColorMenu;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;
import rip.orbit.ostaff.util.ItemBuilder;
import rip.orbit.ostaff.util.menu.object.Button;
import rip.orbit.ostaff.util.menu.object.Menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author LBuddyBoy (lbuddyboy.me)
 * 19/07/2021 / 12:43 AM
 * oStaff / rip.orbit.ostaff.menu
 */
public class EditItemMainMenu extends Menu {

	@Override
	public String getTitle(Player player) {
		return "Edit Your Item's Slots";
	}

	@Override
	public List<Button> buttons(Player player) {
		List<Button> buttons = new ArrayList<>();

		EditItem item = EditItem.byUUID(player.getUniqueId());

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isThruCompassEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.COMPASS).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Thru Compass Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 9;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new ThruCompassSlotMenu().openMenu(player);
				} else {
					item.setThruCompassEnabled(!item.isThruCompassEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isRandomTPEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.WATCH).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Random TP Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 10;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new RandomTPSlotMenu().openMenu(player);
				} else {
					item.setRandomTPEnabled(!item.isRandomTPEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isVanishEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.INK_SACK).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setData((player.hasMetadata("modmode") ? 10 : 7)).setDisplayName(CC.chat("&6Vanish Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 11;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new VanishSlotMenu().openMenu(player);
				} else {
					item.setVanishEnabled(!item.isVanishEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isBetterViewEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.CARPET).setLore(CC.chat(Arrays.asList("" +
						"&7Left Click to modify the slot",
						"&7Middle Click to modify the color",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Better View Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 12;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.MIDDLE) {
					new EditBetterViewColorMenu().openMenu(player);
				} else if (event.getClick() == ClickType.LEFT) {
					new BetterViewSlotMenu().openMenu(player);
				} else {
					item.setBetterViewEnabled(!item.isBetterViewEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isReportsEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.PAPER).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Reports Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 14;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new ReportsSlotMenu().openMenu(player);
				} else {
					item.setReportsEnabled(!item.isReportsEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isFreezerEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.ICE).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Freezer Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 15;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new FreezerSlotMenu().openMenu(player);
				} else {
					item.setFreezerEnabled(!item.isFreezerEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isInspectorEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.BOOK).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6Inspector Slot Editor " + status)).create();
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public int slot() {
				return 16;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new InspectorSlotMenu().openMenu(player);
				} else {
					item.setInspectorEnabled(!item.isInspectorEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isWorldEditEnabled() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.WOOD_AXE).setLore(CC.chat(Arrays.asList("" +
								"&7Left Click to modify the slot",
						"&7Right Click to toggle/enable it"
				))).setDisplayName(CC.chat("&6World Edit Slot Editor " + status
				)).create();
			}

			@Override
			public int slot() {
				return 17;
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				if (event.getClick() == ClickType.LEFT) {
					new WorldEditSlotMenu().openMenu(player);
				} else {
					item.setWorldEditEnabled(!item.isWorldEditEnabled());
					item.save();

					oStaff.getInstance().getStaffModeHandler().unloadStaffMode(player);
					oStaff.getInstance().getStaffModeHandler().loadStaffMode(player);
				}
			}
		});

		buttons.add(new Button() {
			@Override
			public ItemStack stack(Player player) {
				String status = (item.isStaffModeOnJoin() ? CC.chat("&a(Enabled)") : CC.chat("&c(Disabled)"));
				return new ItemBuilder(Material.SIGN).setLore(CC.chat(Collections.singletonList("" +
						"&7Click to toggle on/off your staff mode on join"
				))).setDisplayName(CC.chat("&6Staff Mode On Join Toggler " + status
				)).create();
			}

			@Override
			public int slot() {
				return 31;
			}

			@Override
			public boolean isUpdateAfterClick(Player player) {
				return true;
			}

			@Override
			public void action(Player player, int slot, InventoryClickEvent event) {
				item.setStaffModeOnJoin(!item.isStaffModeOnJoin());
				item.save();
			}
		});

		return buttons;
	}

	@Override
	public int size(Player player) {
		return 45;
	}

	@Override
	public boolean isAutoFill(Player player) {
		return true;
	}
}
