package rip.orbit.ostaff.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import rip.orbit.ostaff.oStaff;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class MongoHandler {

    private final MongoClient client;
    private MongoDatabase mongoDatabase;
    private final MongoCollection profiles;

    private final Gson GSON = new Gson();
    private final Type LIST_STRING_TYPE = new TypeToken<List<String>>() {}.getType();
    private final Type LIST_UUID_TYPE = new TypeToken<List<UUID>>() {}.getType();
    private final Type MAP_STRING_LONG = new TypeToken<Map<String, Long>>() {}.getType();

    public MongoHandler(JavaPlugin plugin) {
        FileConfiguration mainConfig = oStaff.getInstance().getConfig();
        if(mainConfig.getBoolean("MONGO.AUTH.ENABLED"))
            client = new MongoClient(new ServerAddress(plugin.getConfig().getString("MONGO.HOST"), plugin.getConfig().getInt("MONGO.PORT")), Arrays.asList(MongoCredential.createCredential(plugin.getConfig().getString("MONGO.AUTH.USER"), "admin", plugin.getConfig().getString("MONGO.AUTH.PASSWORD").toCharArray())));
        else
            client = new MongoClient(new ServerAddress(plugin.getConfig().getString("MONGO.HOST"), plugin.getConfig().getInt("MONGO.PORT")));


        mongoDatabase = client.getDatabase("Staff");

        profiles = mongoDatabase.getCollection("staffplayers");
    }
}