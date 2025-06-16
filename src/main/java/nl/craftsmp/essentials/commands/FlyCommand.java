package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@AutoRegister
@Command({"fly", "flight"})
@Description("Allows you to set your fly state.")
public class FlyCommand {

    @CommandPermission("craftessentials.command.fly")
    public void flyCommand(Player player) {
        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
            ChatUtils.sendMessage(player, "<primary>Je hebt je vlieg modus <secondary>ingeschakeld<primary>.");
            return;
        }


        player.setAllowFlight(false);
        player.setFlying(false);
        ChatUtils.sendMessage(player, "<primary>Je hebt je vlieg modus <secondary>uitgeschakeld<primary>.");
    }

    @CommandPermission("craftessentials.command.fly.others")
    public void flyCommandOthers(CommandSender sender, Player target) {
        if (!target.getAllowFlight()) {
            target.setAllowFlight(true);
            ChatUtils.sendMessage(sender, "<primary>Je hebt vliegen <secondary>ingeschakeld <primary>voor <secondary><target><primary>.",
                    Placeholder.unparsed("target", target.getName())
            );
            return;
        }


        target.setAllowFlight(false);
        target.setFlying(false);

        ChatUtils.sendMessage(sender, "<primary>Je hebt vliegen <secondary>uitgeschakeld <primary>voor <secondary><target><primary>.",
                Placeholder.unparsed("target", target.getName())
        );
    }
}
