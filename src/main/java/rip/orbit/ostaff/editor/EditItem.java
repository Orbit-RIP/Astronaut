package rip.orbit.ostaff.editor;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import rip.orbit.ostaff.oStaff;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @author LBuddyBoy (lbuddyboy.me)
 * 19/07/2021 / 12:29 AM
 * oStaff / rip.orbit.ostaff.editor
 */

@Getter
@Setter
public class EditItem {

	@Getter private static Map<UUID, EditItem> itemEdits = new HashMap<>();

	private final oStaff plugin = oStaff.getInstance();
	private final MongoCollection<Document> collection = oStaff.getInstance().getMongoHandler().getMongoDatabase().getCollection("edititems");

	private final UUID uuid;

	private int compassSlot = 0;
	private int randomTPSlot = 1;
	private int vanishSlot = 2;
	private int worldEditSlot = 2;
	private int betterViewSlot = 4;
	private int staffOnlineSlot = 6;
	private int freezerSlot = 7;
	private int inspectorSlot = 8;
	private int betterViewData = 0;

	private boolean staffModeOnJoin = true;
	private boolean betterViewEnabled = true;
	private boolean freezerEnabled = true;
	private boolean inspectorEnabled = true;
	private boolean randomTPEnabled = true;
	private boolean staffOnlineEnabled = true;
	private boolean thruCompassEnabled = true;
	private boolean vanishEnabled = true;
	private boolean worldEditEnabled = true;

	public EditItem(UUID uuid) {
		this.uuid = uuid;

		load();
	}

	public void load() {
		Document document = collection.find(Filters.eq("uuid", this.uuid.toString())).first();

		if (document == null)
			return;

		this.compassSlot = document.getInteger("compassSlot");
		this.randomTPSlot = document.getInteger("randomTPSlot");
		this.vanishSlot = document.getInteger("vanishSlot");
		this.betterViewSlot = document.getInteger("betterViewSlot");
		this.staffOnlineSlot = document.getInteger("staffOnlineSlot");
		this.freezerSlot = document.getInteger("freezerSlot");
		this.inspectorSlot = document.getInteger("inspectorSlot");
		this.worldEditSlot = document.getInteger("worldEditSlot");
		this.betterViewData = document.getInteger("betterViewData");

		this.betterViewEnabled = document.getBoolean("betterViewEnabled");
		this.freezerEnabled = document.getBoolean("freezerEnabled");
		this.inspectorEnabled = document.getBoolean("inspectorEnabled");
		this.randomTPEnabled = document.getBoolean("randomTPEnabled");
		this.staffOnlineEnabled = document.getBoolean("staffOnlineEnabled");
		this.thruCompassEnabled = document.getBoolean("thruCompassEnabled");
		this.vanishEnabled = document.getBoolean("vanishEnabled");
		this.worldEditEnabled = document.getBoolean("worldEditEnabled");
		this.staffModeOnJoin = document.getBoolean("staffModeOnJoin");
	}

	public void save() {
		CompletableFuture.runAsync(() -> {
			Document document = new Document();

			document.put("uuid", this.uuid.toString());
			document.put("compassSlot", this.compassSlot);
			document.put("randomTPSlot", this.randomTPSlot);
			document.put("vanishSlot", this.vanishSlot);
			document.put("betterViewSlot", this.betterViewSlot);
			document.put("staffOnlineSlot", this.staffOnlineSlot);
			document.put("freezerSlot", this.freezerSlot);
			document.put("inspectorSlot", this.inspectorSlot);
			document.put("worldEditSlot", this.worldEditSlot);
			document.put("betterViewData", this.betterViewData);

			document.put("betterViewEnabled", this.betterViewEnabled);
			document.put("freezerEnabled", this.freezerEnabled);
			document.put("inspectorEnabled", this.inspectorEnabled);
			document.put("randomTPEnabled", this.randomTPEnabled);
			document.put("staffOnlineEnabled", this.staffOnlineEnabled);
			document.put("thruCompassEnabled", this.thruCompassEnabled);
			document.put("vanishEnabled", this.vanishEnabled);
			document.put("worldEditEnabled", this.worldEditEnabled);
			document.put("staffModeOnJoin", this.staffModeOnJoin);

			collection.replaceOne(Filters.eq("uuid", this.uuid.toString()), document, new ReplaceOptions().upsert(true));
		});
	}

	public static EditItem byUUID(UUID toSearch) {

		for (EditItem value : getItemEdits().values()) {
			if (value.getUuid() == toSearch) {
				return value;
			}
		}

		return new EditItem(toSearch);
	}

}
