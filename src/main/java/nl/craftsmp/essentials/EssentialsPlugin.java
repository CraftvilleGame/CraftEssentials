package nl.craftsmp.essentials;

import lombok.Getter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.commands.*;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

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

        lamp.register(new GamemodeCommand());
        lamp.register(new TeleportCommand());
        lamp.register(new HealCommand());
        lamp.register(new FlyCommand());
        lamp.register(new EnderchestCommand());
        lamp.register(new LoopCommand());
    }


}
