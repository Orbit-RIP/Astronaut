package rip.orbit.ostaff.nametag;

import cc.fyre.proton.nametag.construct.NameTagInfo;
import cc.fyre.proton.nametag.provider.NameTagProvider;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import rip.orbit.ostaff.oStaff;

public class NametagProvider extends NameTagProvider {
    public NametagProvider() {
        super("qModSuite Provider", 10);
    }

    public NameTagInfo fetchNameTag(Player toRefresh, Player refreshFor) {
        if (oStaff.getInstance().getStaffModeHandler().isVanished(toRefresh)) {
            return createNameTag(ChatColor.GRAY.toString(), "");
        }
        return null;
    }
}

