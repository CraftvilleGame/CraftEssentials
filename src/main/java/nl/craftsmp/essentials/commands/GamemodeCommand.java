package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.annotation.Single;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.annotation.CommandPermission;

@Command({"gamemode", "gm"})
public class GamemodeCommand {

    @CommandPermission("craftessentials.command.gamemode")
    public void gamemodeCommand(
            CommandSender sender,
            @Suggest({"creative", "survival", "adventure", "spectator"}) String input,
            @Optional @Single Player target
    ) {
        GameMode mode = parseMode(input);
        if (mode == null) {
            sender.sendMessage(ChatUtils.color("<danger>Ongeldige mode opgegeven."));
            return;
        }

        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(ChatUtils.color("<danger>Enkel spelers kunnen dit gebruiken!"));
                return;
            }

            player.setGameMode(mode);
            sender.sendMessage(ChatUtils.color("<primary>Je hebt je gamemode aangepast naar <secondary><gamemode><primary>.",
                    Placeholder.unparsed("gamemode", modeName(mode))
            ));
            return;
        }

        target.setGameMode(mode);
        sender.sendMessage(ChatUtils.color("<primary>Je de gamemode van <secondary><target> <primary>veranderd naar <secondary><gamemode><primary>.",
                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("gamemode", modeName(mode))
        ));
    }

    @Command("gmc")
    @CommandPermission("craftessentials.command.gamemode")
    public void gamemodeCreative(CommandSender sender, @Optional @Single Player target) {
        gamemodeCommand(sender, "Creative", target);
    }

    @Command("gms")
    @CommandPermission("craftessentials.command.gamemode")
    public void gamemodeSurvival(CommandSender sender, @Optional @Single Player target) {
        gamemodeCommand(sender, "Survival", target);
    }

    @Command("gma")
    @CommandPermission("craftessentials.command.gamemode")
    public void gamemodeAdventure(CommandSender sender, @Optional @Single Player target) {
        gamemodeCommand(sender, "Adventure", target);
    }

    @Command("gmsp")
    @CommandPermission("craftessentials.command.gamemode")
    public void gamemodeSpectator(CommandSender sender, @Optional @Single Player target) {
        gamemodeCommand(sender, "Spectator", target);
    }

    private String modeName(GameMode mode) {
        return switch (mode) {
            case CREATIVE -> "Creative";
            case SURVIVAL -> "Survival";
            case ADVENTURE -> "Adventure";
            case SPECTATOR -> "Spectator";
        };
    }

    private GameMode parseMode(String input) {
        return switch (input.toUpperCase()) {
            case "CREATIVE", "CREA", "C", "1" -> GameMode.CREATIVE;
            case "SURVIVAL", "SURV", "S", "0" -> GameMode.SURVIVAL;
            case "ADVENTURE", "ADVEN", "A", "2" -> GameMode.ADVENTURE;
            case "SPECTATOR", "SPEC", "SP", "3" -> GameMode.SPECTATOR;
            default -> null;
        };
    }

}
