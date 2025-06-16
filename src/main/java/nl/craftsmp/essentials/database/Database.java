package nl.craftsmp.essentials.database;

import com.craftmend.storm.Storm;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.configuration.GlobalConfiguration;
import nl.craftsmp.essentials.database.adapters.DatabaseAdapter;
import nl.craftsmp.essentials.database.adapters.MySQLAdapter;
import nl.craftsmp.essentials.database.adapters.SQLiteAdapter;

@Getter
@Accessors(fluent = true)
public class Database {

    @Setter
    private Storm storm;

    public void connect() {
        GlobalConfiguration configuration = EssentialsPlugin.instance().globalConfiguration();
        AdapterType type = configuration.adapterType();

        EssentialsPlugin.instance().getLogger().info("Connecting to database with " + type.name());

        DatabaseAdapter adapter = switch (type) {
            case MYSQL -> new MySQLAdapter();
            case SQLITE -> new SQLiteAdapter();
        };

        adapter.connect();
        EssentialsPlugin.instance().getLogger().info("Connected to the database.");
    }

}
