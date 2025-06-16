package nl.craftsmp.essentials.configuration;

import lombok.Getter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.database.AdapterType;
import nl.craftsmp.essentials.utils.PluginConfiguration;

@Getter
@Accessors(fluent = true)
public class GlobalConfiguration extends PluginConfiguration {

    private final AdapterType adapterType;

    private final String mySqlHost, mySqlDatabase, mySqlUsername, mySqlPassword;
    private final int mySqlPort;

    public GlobalConfiguration() {
        super(EssentialsPlugin.instance().getDataFolder(), "config.yml");
        this.loadConfiguration();

        this.adapterType = AdapterType.valueOf(rootNode.node("database", "type").getString("sqlite").toUpperCase());

        this.mySqlHost = rootNode.node("database", "mysql", "host").getString("172.18.0.1");
        this.mySqlPort = rootNode.node("database", "mysql", "port").getInt(3306);
        this.mySqlDatabase = rootNode.node("database", "mysql", "database").getString("database");
        this.mySqlUsername = rootNode.node("database", "mysql", "username").getString("username");
        this.mySqlPassword = rootNode.node("database", "mysql", "password").getString("password");
    }
}
