package nl.craftsmp.essentials.database.adapters;

import com.craftmend.storm.Storm;
import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.connection.hikaricp.HikariDriver;
import com.zaxxer.hikari.HikariConfig;
import lombok.SneakyThrows;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.configuration.GlobalConfiguration;
import nl.craftsmp.essentials.models.WarpModel;
import org.bukkit.Bukkit;

public class MySQLAdapter implements DatabaseAdapter {

    @Override
    public void connect() {
        GlobalConfiguration configuration = EssentialsPlugin.instance().globalConfiguration();

        try {
            HikariConfig config = new HikariConfig();
            config.setDriverClassName("org.mariadb.jdbc.Driver");
            config.setJdbcUrl("jdbc:mariadb://" + configuration.mySqlHost() + ":" + configuration.mySqlPort() + "/" + configuration.mySqlDatabase());
            config.setUsername(configuration.mySqlUsername());
            config.setPassword(configuration.mySqlPassword());
            config.setMaximumPoolSize(6);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.setConnectionTestQuery("SELECT 1");

            Storm storm = new Storm(new HikariDriver(config));
            EssentialsPlugin.instance().database().storm(storm);
        } catch (Exception e) {
            EssentialsPlugin.instance().getLogger().severe("Couldn't connect to the MySQL Database: " + e.getMessage());
            EssentialsPlugin.instance().getLogger().severe("Plugin will shutdown!");
            Bukkit.getServer().getPluginManager().disablePlugin(EssentialsPlugin.instance());
        }
    }

    public void registerModels() {
        registerStormModel(new WarpModel());
    }

    @SneakyThrows
    private void registerStormModel(StormModel model) {
        Storm storm = EssentialsPlugin.instance().database().storm();

        storm.registerModel(model);
        storm.runMigrations();
    }

}
