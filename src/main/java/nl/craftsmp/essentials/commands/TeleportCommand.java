package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"teleport", "tp"})
public class TeleportCommand {

    // teleport <target>
    @CommandPermission("craftessentials.command.teleport")
    public void teleportCommand(Player sender, Entity target) {
        sender.teleport(target);

        ChatUtils.sendMessage(sender, "<primary>Je bent geteleporteerd naar <secondary><target><primary>.",
                Placeholder.unparsed("player", sender.getName()),
                Placeholder.unparsed("target", target.getName())
        );
    }

    // teleport <target_one> <target_two>
    @CommandPermission("craftessentials.command.teleport")
    public void teleportCommand(Player sender, Entity targetOne, Entity targetTwo) {
        targetOne.teleport(targetTwo);

        ChatUtils.sendMessage(sender, "<primary>Teleporteerde <secondary><target_one> <primary>naar <secondary><target_two><primary>.",
                Placeholder.unparsed("player", sender.getName()),
                Placeholder.unparsed("target_one", targetOne.getName()),
                Placeholder.unparsed("target_two", targetTwo.getName())
        );
    }

    // teleport <x> <y> <z>
    @CommandPermission("craftessentials.command.teleport")
    public void teleportCommand(Player sender, @Suggest("~ ~ ~") Location location) {
        sender.teleport(location);

        ChatUtils.sendMessage(sender, "<primary>Je bent geteleporteerd naar <secondary><x>, <y>, <z><primary>.",
                Placeholder.unparsed("player", sender.getName()),
                Placeholder.unparsed("x", String.valueOf(location.x())),
                Placeholder.unparsed("y", String.valueOf(location.y())),
                Placeholder.unparsed("z", String.valueOf(location.z()))
        );
    }

    // teleport <target> <x> <y> <z>
    @CommandPermission("craftessentials.command.teleport")
    public void teleportCommand(Player sender, Entity target, @Suggest("~ ~ ~") Location location) {
        target.teleport(location);

        ChatUtils.sendMessage(sender, "<primary>Je hebt <secondary><target> <primary>geteleporteerd naar <secondary><x>, <y>, <z><primary>.",
                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("player", sender.getName()),
                Placeholder.unparsed("x", String.valueOf(location.x())),
                Placeholder.unparsed("y", String.valueOf(location.y())),
                Placeholder.unparsed("z", String.valueOf(location.z()))
        );
    }

    @Command({"teleporthere", "tph", "s"})
    @CommandPermission("craftessentials.command.teleport")
    public void teleportHereCommand(Player sender, Entity target) {
        target.teleport(sender);

        ChatUtils.sendMessage(sender, "<primary>Je hebt <secondary><target> <primary>naar jou geteleporteerd.",
                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("player", sender.getName())
        );
    }

}
