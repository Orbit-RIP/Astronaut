package rip.orbit.ostaff.editor.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import rip.orbit.ostaff.editor.EditItem;

/**
 * @author LBuddyBoy (lbuddyboy.me)
 * 19/07/2021 / 12:53 AM
 * oStaff / rip.orbit.ostaff.editor.listener
 */
public class EditItemListener implements Listener {

	@EventHandler
	public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {

		EditItem profile = new EditItem(event.getUniqueId());

		EditItem.getItemEdits().put(profile.getUuid(), profile);
	}

}
