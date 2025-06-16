package nl.craftsmp.essentials;

import lombok.Getter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.annotation.AutoRegister;
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

    private Lamp<BukkitCommandActor> lamp;

    @Override
    public void onEnable() {
        instance = this;

        lamp = BukkitLamp.builder(this)
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
                getLogger().severe("Couldn't register: " + clazz.getSimpleName());
            }
        }
    }


}
