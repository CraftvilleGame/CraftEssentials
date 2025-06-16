package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.managers.WarpManager;
import nl.craftsmp.essentials.models.WarpModel;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Dependency;
import revxrsal.commands.annotation.Single;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@AutoRegister
@Command({"warp"})
public class WarpCommand {

    @Dependency
    private WarpManager warpManager;

    @CommandPermission("craftessentials.command.warp.teleport")
    public void warp(Player player, WarpModel warpModel) {
        player.teleport(warpModel.location());

        player.sendMessage(ChatUtils.color("<primary>Je bent geteleporteerd naar <secondary><name><primary>.",
                Placeholder.unparsed("name", warpModel.name()))
        );
    }

    @Subcommand({"create"})
    @CommandPermission("craftessentials.command.warp.create")
    public void createWarp(Player player, @Single String name) {
        if (name.length() > 32) {
            ChatUtils.sendMessage(player, "<danger>Zorg dat de naam minder dan 32 tekens bevat.");
            return;
        }

        WarpManager warpManager = EssentialsPlugin.instance().warpManager();
        if (warpManager.getWarp(name) != null) {
            ChatUtils.sendMessage(player, "<danger>Er is al een warp met deze naam.");
            return;
        }

        WarpModel warpModel = new WarpModel();
        warpModel.name(name);
        warpModel.location(player.getLocation());
        warpManager.registerWarp(warpModel);

        ChatUtils.sendMessage(player, "<primary>Je hebt de warp <secondary><warp> <primary>aangemaakt.",
                Placeholder.unparsed("warp", warpModel.name())
        );
    }

    @Subcommand({"delete"})
    @CommandPermission("craftessentials.command.warp.delete")
    public void deleteWarp(Player player, WarpModel warpModel) {
        WarpManager warpManager = EssentialsPlugin.instance().warpManager();
        warpManager.deleteWarp(warpModel);

        ChatUtils.sendMessage(player, "<primary>Je hebt de warp <secondary><warp> <primary>verwijderd.",
                Placeholder.unparsed("warp", warpModel.name())
        );
    }

}
