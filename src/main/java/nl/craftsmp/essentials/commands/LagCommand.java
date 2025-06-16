package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@AutoRegister
public class LagCommand {

    @Command({"lag", "gc"})
    @CommandPermission("craftessentials.command.lag")
    public void lagCommand(CommandSender sender) {

        Runtime runtime = Runtime.getRuntime();
        double tps = Bukkit.getTPS()[0];


        ChatUtils.sendMessage(sender, "<primary>Current TPS: <tps><reset><br>" +
                        "<primary>Maximum Memory: <secondary><max_memory><br>" +
                        "<primary>Allocated Memory: <secondary><allocated_memory><br>" +
                        "<primary>Free Memory: <secondary><free_memory>",
                Placeholder.component("tps", coloredTps(tps)),
                Placeholder.unparsed("max_memory", String.valueOf(runtime.maxMemory() / 1024 / 1024)),
                Placeholder.unparsed("allocated_memory", String.valueOf(runtime.totalMemory() / 1024 / 1024)),
                Placeholder.unparsed("free_memory", String.valueOf(runtime.freeMemory() / 1024 / 1024))
        );

        ChatUtils.sendMessage(sender, "");

        for (World world : Bukkit.getWorlds()) {
            String environment = getEnvironmentType(world);

            int tileEntities = 0;
            for (@NotNull Chunk loadedChunk : world.getLoadedChunks()) {
                tileEntities += loadedChunk.getTileEntities().length;
            }

            ChatUtils.sendMessage(sender, "<primary>World <secondary><world> <primary>(<environment>): <secondary><chunks> <primary>chunks, <secondary><entities> <primary>entities, <secondary><tiles> <primary>tiles.",
                    Placeholder.unparsed("world", world.getName()),
                    Placeholder.unparsed("environment", environment),
                    Placeholder.unparsed("chunks", String.valueOf(world.getLoadedChunks().length)),
                    Placeholder.unparsed("entities", String.valueOf(world.getEntities().size())),
                    Placeholder.unparsed("tiles", String.valueOf(tileEntities))
            );
        }
    }

    private Component coloredTps(double tps) {
        NamedTextColor color;

        if (tps >= 18.0) {
            color = NamedTextColor.DARK_GREEN;
        } else if (tps >= 15.0) {
            color = NamedTextColor.YELLOW;
        } else {
            color = NamedTextColor.RED;
        }
        return Component.text((int) tps, color);
    }

    private String getEnvironmentType(World world) {
        return switch (world.getEnvironment()) {
            case NETHER -> "Nether";
            case THE_END -> "The End";
            case NORMAL -> "Normal";
            case CUSTOM -> "Custom";
        };
    }

}
