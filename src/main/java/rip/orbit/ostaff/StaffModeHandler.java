package rip.orbit.ostaff;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import rip.orbit.ostaff.editor.EditItem;
import rip.orbit.ostaff.editor.listener.EditItemListener;
import rip.orbit.ostaff.listener.StaffModeListener;
import rip.orbit.ostaff.util.CC;
import rip.orbit.ostaff.util.ItemBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class StaffModeHandler {

	private oStaff plugin;

	public HashMap<UUID, ItemStack[]> prevInventory;
	public HashMap<UUID, ItemStack[]> prevInventoryArmor;

	@Getter
	private final static Map<Player, StaffModeHandler> staffModeMap = new HashMap<>();

	private ItemStack thruCompass = new ItemBuilder(Material.COMPASS).setDisplayName(CC.chat("&6Phase Compass")).create();
	private ItemStack randomTP = new ItemBuilder(Material.WATCH).setDisplayName(CC.chat("&6Random Teleport")).create();
	private ItemStack vanishon = new ItemBuilder(Material.INK_SACK).setData(10).setDisplayName(CC.chat("&6Vanish &a[On]")).create();
	private ItemStack vanishoff = new ItemBuilder(Material.INK_SACK).setData(8).setDisplayName(CC.chat("&6Vanish &c[Off]")).create();
	private ItemStack betterview = new ItemBuilder(Material.CARPET).setDisplayName(CC.chat("&6Better View")).create();
	private ItemStack staffOnline = new ItemBuilder(Material.SKULL_ITEM).setDisplayName(CC.chat("&6Staff Online")).create();
	private ItemStack freezer = new ItemBuilder(Material.ICE).setDisplayName(CC.chat("&6Freezer")).create();
	private ItemStack inspector = new ItemBuilder(Material.BOOK).setDisplayName(CC.chat("&6Inspector")).create();
	private ItemStack worldedit = new ItemBuilder(Material.WOOD_AXE).setDisplayName(CC.chat("&6WorldEdit Wand")).create();

	public StaffModeHandler(oStaff plugin) {
		this.plugin = plugin;

		prevInventory = new HashMap<>();
		prevInventoryArmor = new HashMap<>();

		Bukkit.getPluginManager().registerEvents(new StaffModeListener(plugin), plugin);
		Bukkit.getPluginManager().registerEvents(new EditItemListener(), plugin);
	}

	public void loadStaffMode(Player player) {
		EditItem editItem = EditItem.byUUID(player.getUniqueId());
		player.setGameMode(GameMode.CREATIVE);
		prevInventory.put(player.getUniqueId(), player.getInventory().getContents());
		prevInventoryArmor.put(player.getUniqueId(), player.getInventory().getArmorContents());
		player.getInventory().clear();
		player.setMetadata("modmode", new FixedMetadataValue(plugin, player));
		staffModeMap.put(player, this);
		if (player.isOp()) {
			if (editItem.isWorldEditEnabled()) {
				player.getInventory().setItem(editItem.getWorldEditSlot(), worldedit);
			}
		}

		ItemStack stack = betterview.clone();
		stack.setDurability((short) editItem.getBetterViewData());
		if (editItem.isThruCompassEnabled()) {
			player.getInventory().setItem(editItem.getCompassSlot(), thruCompass);
		}
		if (editItem.isRandomTPEnabled()) {
			player.getInventory().setItem(editItem.getRandomTPSlot(), randomTP);
		}
		if (editItem.isVanishEnabled()) {
			player.getInventory().setItem(editItem.getVanishSlot(), vanishon);
		}
		if (editItem.isBetterViewEnabled()) {
			player.getInventory().setItem(editItem.getBetterViewSlot(), stack);
		}
		if (editItem.isStaffOnlineEnabled()) {
			player.getInventory().setItem(editItem.getStaffOnlineSlot(), staffOnline);
		}
		if (editItem.isFreezerEnabled()) {
			player.getInventory().setItem(editItem.getFreezerSlot(), freezer);
		}
		if (editItem.isInspectorEnabled()) {
			player.getInventory().setItem(editItem.getInspectorSlot(), inspector);
		}
		loadVanish(player);
		player.updateInventory();
	}

	public void unloadStaffMode(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(prevInventoryArmor.get(player.getUniqueId()));
		prevInventoryArmor.remove(player.getUniqueId());
		player.getInventory().setContents(prevInventory.get(player.getUniqueId()));
		prevInventory.remove(player.getUniqueId());
		player.removeMetadata("modmode", plugin);
		staffModeMap.remove(player);
		unloadVanish(player);
		player.setGameMode(GameMode.SURVIVAL);
		player.updateInventory();
	}

	public void loadVanish(Player player) {
		player.setMetadata("invisible", new FixedMetadataValue(plugin, player));
		for (Player on : Bukkit.getOnlinePlayers()) {
			if (!on.hasPermission("orbit.staff")) {
				on.hidePlayer(player);
			}
		}
	}

	public void unloadVanish(Player player) {
		player.removeMetadata("invisible", plugin);
		for (Player on : Bukkit.getOnlinePlayers()) {
			if (!on.hasPermission("orbit.staff")) {
				on.showPlayer(player);
			}
		}
	}

	public boolean inStaffMode(Player player) {
		if (player.hasMetadata("modmode")) {
			return true;
		} else {
			return false;
		}
	}
	public boolean isVanished(Player player) {
		if (player.hasMetadata("invisible")) {
			return true;
		} else {
			return false;
		}
	}
}
