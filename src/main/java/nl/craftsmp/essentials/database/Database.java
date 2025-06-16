package nl.craftsmp.essentials.database;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.configuration.GlobalConfiguration;
import nl.craftsmp.essentials.database.types.DatabaseAdapter;
import nl.craftsmp.essentials.database.types.MySQLAdapter;
import nl.craftsmp.essentials.database.types.SQLiteAdapter;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@Accessors(fluent = true)
public class Database {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Setter
    private Storm storm;

    public void connect() {
        GlobalConfiguration configuration = EssentialsPlugin.instance().globalConfiguration();
        DatabaseType type = configuration.databaseType();

        EssentialsPlugin.instance().getLogger().info("Connecting to database with " + type.name());

        DatabaseAdapter adapter = switch (type) {
            case MYSQL -> new MySQLAdapter();
            case SQLITE -> new SQLiteAdapter();
        };

        adapter.connect();
        EssentialsPlugin.instance().getLogger().info("Connected to the database.");
    }

    public void saveModel(StormModel model) {
        try {
            storm.save(model);
        } catch (SQLException e) {
            EssentialsPlugin.instance().getLogger().severe("Couldn't save model: " + e.getMessage());
        }
    }

}
