package nl.craftsmp.essentials.database.adapters;

import com.craftmend.storm.Storm;
import com.craftmend.storm.connection.sqlite.SqliteFileDriver;
import nl.craftsmp.essentials.EssentialsPlugin;
import org.bukkit.Bukkit;

import java.io.File;

public class SQLiteAdapter extends MySQLAdapter {

    @Override
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            Storm storm = new Storm(new SqliteFileDriver(new File(EssentialsPlugin.instance().getDataFolder(), "data.db")));
            EssentialsPlugin.instance().database().storm(storm);
            registerModels();
        } catch (Exception e) {
            EssentialsPlugin.instance().getLogger().severe("Couldn't connect to the MySQL Database: " + e.getMessage());
            EssentialsPlugin.instance().getLogger().severe("Plugin will shutdown!");
            Bukkit.getServer().getPluginManager().disablePlugin(EssentialsPlugin.instance());
        }
    }
}
