package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@AutoRegister
@Command({"enderchest", "ec"})
@Description("Opens a enderchest.")
public class EnderchestCommand {

    @CommandPermission("craftessentials.command.enderchest")
    public void enderChestCommand(Player player) {
        player.openInventory(player.getEnderChest());
        ChatUtils.sendMessage(player, "<primary>Je hebt je enderchest geopend.");
    }

    @CommandPermission("craftessentials.command.enderchest.others")
    public void enderChestCommandOthers(Player player, Player target) {
        player.openInventory(target.getEnderChest());
        ChatUtils.sendMessage(player, "<primary>Je hebt de enderchest van <secondary><target> <primary>geopend.",
                Placeholder.unparsed("target", target.getName())
        );
    }

}
