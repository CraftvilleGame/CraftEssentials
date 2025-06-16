package nl.craftsmp.essentials.commands;

import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@AutoRegister
public class TopCommand {

    @Command({"top"})
    @CommandPermission("craftessentials.command.top")
    public void topCommand(Player player) {
        Location location = player.getLocation();
        World world = location.getWorld();

        Location highestLocation = world.getHighestBlockAt(location).getLocation();

        if(highestLocation.getY() <= -64) {
            ChatUtils.sendMessage(player, "<danger>Can't teleport to a safe location.");
            return;
        }

        highestLocation.setY(highestLocation.getY() + 1.1);
        highestLocation.setX(highestLocation.getX() + 0.5);
        highestLocation.setZ(highestLocation.getZ() + 0.5);

        player.teleport(highestLocation);

        ChatUtils.sendMessage(player, "<primary>Je bent naar de hoogste locatie geteleporteerd.");
    }

}
