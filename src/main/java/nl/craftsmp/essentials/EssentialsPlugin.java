package nl.craftsmp.essentials;

import lombok.Getter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.configuration.GlobalConfiguration;
import nl.craftsmp.essentials.database.Database;
import nl.craftsmp.essentials.managers.WarpManager;
import nl.craftsmp.essentials.models.WarpModel;
import nl.craftsmp.essentials.utils.commands.WarpParameterType;
import nl.craftsmp.essentials.utils.commands.WarpSuggestionProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.util.Set;

import static org.reflections.scanners.Scanners.SubTypes;
import static org.reflections.scanners.Scanners.TypesAnnotated;

@Getter
@Accessors(fluent = true)
public class EssentialsPlugin extends JavaPlugin {

    @Getter
    private static EssentialsPlugin instance;

    private Database database;
    private GlobalConfiguration globalConfiguration;
    private Lamp<BukkitCommandActor> lamp;

    private WarpManager warpManager;

    @Override
    public void onEnable() {
        instance = this;

        globalConfiguration = new GlobalConfiguration();
        globalConfiguration.saveConfiguration();

        database = new Database();
        database.connect();

        warpManager = new WarpManager();
        warpManager.loadWarps();

        lamp = BukkitLamp.builder(this)
                .parameterTypes(builder -> {
                    builder.addParameterType(WarpModel.class, new WarpParameterType());
                })
                .suggestionProviders(builder -> {
                    builder.addProvider(WarpModel.class, new WarpSuggestionProvider());
                })
                .dependency(WarpManager.class, warpManager)
                .build();

        autoRegisterCommands();
    }

    private void autoRegisterCommands() {
        Reflections reflections = new Reflections("nl.craftsmp.essentials");
        Set<Class<?>> annotatedClasses = reflections.get(SubTypes.of(TypesAnnotated.with(AutoRegister.class)).asClass());

        for (Class<?> clazz : annotatedClasses) {
            try {
                Object command = clazz.getDeclaredConstructor().newInstance();
                lamp.register(command);

                getLogger().info("Registering command: " + clazz.getSimpleName());
            } catch (Exception e) {
                getLogger().severe("Couldn't register: " + clazz.getSimpleName() + ": " + e.getMessage());
            }
        }
    }


}
