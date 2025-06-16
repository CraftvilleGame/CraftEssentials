package nl.craftsmp.essentials.utils;

import lombok.Getter;
import nl.craftsmp.essentials.EssentialsPlugin;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.loader.HeaderMode;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.File;
import java.io.IOException;

public abstract class PluginConfiguration {

    private final File file;
    private final String name;

    protected @Getter YamlConfigurationLoader loader;
    protected @Getter CommentedConfigurationNode rootNode;

    public PluginConfiguration(File file, String name) {
        this.file = file;
        this.name = name;
    }


    public void loadConfiguration() {
        loader = YamlConfigurationLoader.builder()
                .path(file.toPath().resolve(name))
                .indent(2)
                .nodeStyle(NodeStyle.BLOCK)
                .headerMode(HeaderMode.PRESET)
                .build();

        try {
            rootNode = loader.load();
        } catch (IOException e) {
            EssentialsPlugin.instance().getLogger().warning("An error occurred while loading this configuration: " + e.getMessage());
        }
    }

    public void saveConfiguration() {
        try {
            loader.save(rootNode);
        } catch (final ConfigurateException e) {
            EssentialsPlugin.instance().getLogger().warning("Unable to save your configuration! Sorry! " + e.getMessage());
        }
    }
}