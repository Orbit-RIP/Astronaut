package rip.orbit.ostaff.listener;

import cc.fyre.proton.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import rip.orbit.nebula.Nebula;
import rip.orbit.nebula.profile.Profile;
import rip.orbit.nebula.rank.Rank;
import rip.orbit.ostaff.StaffModeHandler;
import rip.orbit.ostaff.editor.EditItem;
import rip.orbit.ostaff.oStaff;
import rip.orbit.ostaff.util.CC;
import rip.orbit.ostaff.util.ItemBuilderr;

import java.util.*;

public class StaffModeListener implements Listener {

    private final oStaff pl;

    public StaffModeListener(oStaff plugin) {
        this.pl = plugin;
    }

    @EventHandler
    public void onInteractVanishTool(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack item = event.getItem();
        if (pl.getStaffModeHandler().inStaffMode(p)) {
            if (item == null)
                return;
            if (pl.getStaffModeHandler().getVanishon().isSimilar(item)) {
                p.setItemInHand(pl.getStaffModeHandler().getVanishoff());
                pl.getStaffModeHandler().unloadVanish(p);
            } else if (pl.getStaffModeHandler().getVanishoff().isSimilar(item)) {
                p.setItemInHand(pl.getStaffModeHandler().getVanishon());
                pl.getStaffModeHandler().loadVanish(p);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (pl.getStaffModeHandler().inStaffMode(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractRTPTool(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (pl.getStaffModeHandler().inStaffMode(player)) {
            if (item == null)
                return;
            if (pl.getStaffModeHandler().getRandomTP().isSimilar(item)) {

                ArrayList<Player> pList = new ArrayList<>(Bukkit.getOnlinePlayers());

                int random = new Random().nextInt(pList.size());
                if (pList.get(random) == player)
                    return;
                if (pl.getStaffModeHandler().inStaffMode(pList.get(random)))
                    return;

                player.teleport(pList.get(random));
            } else if (pl.getStaffModeHandler().getStaffOnline().isSimilar(item)) {
                Set<Player> members = StaffModeHandler.getStaffModeMap().keySet();
                int rows = (int) Math.ceil(members.size() / 9.0);
                Inventory inventory = Bukkit.createInventory(null, (rows == 0) ? 9 : (9 * rows), "Online Staff");
                for (Player member : members) {
                    Profile profile = Nebula.getInstance().getProfileHandler().fromUuid(member.getUniqueId());
                    Rank rank = profile.getActiveRank();
                    inventory.addItem(ItemBuilder.of(Material.SKULL_ITEM).data((byte) 3).name(player.getDisplayName()).addToLore(
                            ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------------"),
                            ChatColor.GOLD + "Rank: " + (rank.getFancyName()),
                            ChatColor.GOLD + "Mod Mode: " + (player.hasMetadata("modmode") ? "&aEnabled" : "&cDisabled"),
                            ChatColor.GOLD + "Vanish: " + (player.hasMetadata("invisible") ? "&aEnabled" : "&cDisabled"),
                            "",
                            ChatColor.GRAY.toString() + ChatColor.ITALIC + "Click to teleport this staff member",
                            ChatColor.translateAlternateColorCodes('&', "&7&m---------------------------------")).build());
                }
                player.openInventory(inventory);
                event.setCancelled(true);
            }

            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler
    public void onItemClick(InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals("Online Staff")) {
            if (e.getWhoClicked() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName() != null) {
                if (e.getWhoClicked() instanceof Player) {
                    String name = e.getCurrentItem().getItemMeta().getDisplayName();
                    Player p = (Player) e.getWhoClicked();
                    Player tpTo = Bukkit.getPlayerExact(ChatColor.stripColor(name));
                    p.teleport(tpTo);
                    p.closeInventory();
                    p.sendMessage(ChatColor.GOLD + "Teleporting you to " + ChatColor.WHITE + name + ChatColor.GOLD + ".");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (pl.getStaffModeHandler().inStaffMode(event.getPlayer())) {
            if (event.getPlayer().isOp())
                return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (pl.getStaffModeHandler().inStaffMode(event.getPlayer())) {
            if (event.getPlayer().isOp())
                return;
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractChest(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();
        Player player = event.getPlayer();
        if (pl.getStaffModeHandler().inStaffMode(player)) {
            if (event.getAction().name().equalsIgnoreCase("RIGHT_CLICK_BLOCK")) {
                if (clicked == null)
                    return;
                if (clicked.getType() == Material.CHEST || clicked.getType() == Material.TRAPPED_CHEST) {
                    event.setCancelled(true);
                    player.closeInventory();
                    Inventory inventory = ((Chest) clicked.getState()).getInventory();
//					player.openInventory(inventory);
                    Inventory inv = Bukkit.createInventory(null, 54, "");
                    inv.setContents(inventory.getContents());
                    player.openInventory(inv);
                    player.sendMessage(CC.chat("&7&oOpening chest clicked silently"));
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!event.getPlayer().hasPermission("orbit.staff")) {
                if (oStaff.getInstance().getStaffModeHandler().isVanished(online)) {
                    oStaff.getInstance().getStaffModeHandler().loadVanish(online);
                }
            }
        }
        if (player.hasPermission("orbit.staff")) {
            EditItem editItem = EditItem.byUUID(player.getUniqueId());
            if (editItem.isStaffModeOnJoin()) {
                pl.getStaffModeHandler().loadStaffMode(player);
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("ostaff.staff")) {
            if (pl.getStaffModeHandler().inStaffMode(player)) {
                pl.getStaffModeHandler().unloadStaffMode(player);
            }
        }
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (pl.getStaffModeHandler().inStaffMode(player)) {
                pl.getStaffModeHandler().unloadStaffMode(player);
            }
        }
    }

    @EventHandler
    public void onInteractEntityFreeze(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if (pl.getStaffModeHandler().inStaffMode(player)) {
            Entity e = event.getRightClicked();
            if (e == null)
                return;
            if (e instanceof Player) {
                Player rightClicked = (Player) e;
                if (pl.getStaffModeHandler().getFreezer().isSimilar(player.getItemInHand())) {
                    if (pl.getStaffModeHandler().inStaffMode(rightClicked))
                        return;
                    player.chat("/freeze " + rightClicked.getName());
                } else if (pl.getStaffModeHandler().getInspector().isSimilar(player.getItemInHand())) {
                    targetInspectMap.put(player, rightClicked);
                    openInspectGUI(player);
                }
            }
        }
    }

    @EventHandler
    public void onDamageInStaff(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            if (pl.getStaffModeHandler().inStaffMode(damager)) {
                if (damager.isOp())
                    return;
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropInStaff(PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        if (pl.getStaffModeHandler().inStaffMode(p)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        String title = "%player% Inv";
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (targetInspectMap.isEmpty())
                return;
            if (!targetInspectMap.containsKey(player))
                return;
            if (!event.getInventory().getTitle().equalsIgnoreCase(CC.chat(title.replaceAll("%player%", targetInspectMap.get(player).getName()))))
                return;
            if (event.getInventory().getTitle().equalsIgnoreCase(CC.chat(title.replaceAll("%player%", targetInspectMap.get(player).getName())))) {
                targetInspectMap.remove(player);
            }
        }
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        String title = "%player% Inv";
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            if (pl.getStaffModeHandler().inStaffMode(player)) {
                if (targetInspectMap.isEmpty() || !targetInspectMap.containsKey(player))
                    return;
                if (!event.getInventory().getTitle().equalsIgnoreCase(CC.chat(title.replaceAll("%player%", targetInspectMap.get(player).getName()))))
                    return;
                if (event.getInventory().getTitle().equalsIgnoreCase(CC.chat(title.replaceAll("%player%", targetInspectMap.get(player).getName())))) {

                    Player target = targetInspectMap.get(player);

                    if (item == null)
                        return;

                    if (event.getSlot() == 42) {
                        if (item.getType() == Material.COMPASS) {
                            player.closeInventory();
                            player.teleport(target);
                            return;
                        }
                    }
                    if (event.getSlot() == 44) {
                        if (item.getType() == Material.ICE) {
                            player.closeInventory();
                            player.performCommand("freeze " + target.getName());
                        }
                    }
                }
            }
        }
    }

    public Map<Player, Player> targetInspectMap = new HashMap<>();

    private void openInspectGUI(Player player) {
        Player target = targetInspectMap.get(player);

        Inventory inv;

        String title = "%player% Inv";

        inv = Bukkit.createInventory(null, 45,
                CC.chat(title.replaceAll("%player%", targetInspectMap.get(player).getName())));


        inv.setContents(target.getInventory().getContents());


        if (target.getInventory().getHelmet() != null) {
            inv.setItem(36, target.getInventory().getHelmet());
        }
        if (target.getInventory().getChestplate() != null) {
            inv.setItem(37, target.getInventory().getChestplate());
        }
        if (target.getInventory().getLeggings() != null) {
            inv.setItem(38, target.getInventory().getLeggings());
        }
        if (target.getInventory().getBoots() != null) {
            inv.setItem(39, target.getInventory().getBoots());
        }
        inv.setItem(40, new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7));
        inv.setItem(41, new ItemBuilderr(Material.SPECKLED_MELON).setDisplayName(CC.chat("&cHealth: " + target.getHealth())).create());
        inv.setItem(42, new ItemBuilderr(Material.COMPASS).setDisplayName(CC.chat("&6Teleport to " + target.getName())).create());
        inv.setItem(43, new ItemBuilderr(Material.WATCH)
                .addLore(CC.chat("&7World: " + target.getWorld().getName() + ", X: " + target.getLocation().getBlockX() + ", Z: " + target.getLocation().getBlockZ()))
                .setDisplayName(CC.chat("&6" + target.getName() + "'s Location")).create());
        List<String> builtPotionEffects = new ArrayList<>();
        if (!target.getActivePotionEffects().isEmpty()) {
            for (PotionEffect pe : target.getActivePotionEffects()) {
                builtPotionEffects.add(ChatColor.GRAY + "» " + pe.getType().getName() + " " + pe.getAmplifier() + ": " + pe.getDuration());
            }
        } else {
            builtPotionEffects.add("None");
        }
        ItemStack stack = new ItemBuilderr(Material.GLASS_BOTTLE)
                .setDisplayName(CC.chat("&6" + target.getName() + "'s Effects")).create();
        ItemMeta meta = stack.getItemMeta();

        meta.setLore(builtPotionEffects);

        stack.setItemMeta(meta);
        inv.setItem(44, stack);
        player.openInventory(inv);
    }
}
