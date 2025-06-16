package nl.craftsmp.essentials.commands;

import lombok.Getter;
import lombok.experimental.Accessors;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.tasks.LoopCommandTask;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Accessors(fluent = true)
@Command({"loop"})
public class LoopCommand {

    private Map<UUID, LoopCommandTask> loopTasks = new HashMap<>();

    @CommandPermission("craftessentials.command.loop")
    public void loopCommand(Player player, int amount, int tickDelay, String command) {
        if (loopTasks.containsKey(player.getUniqueId())) {
            ChatUtils.sendMessage(player, "<danger>Je kan maar 1 loop tegelijk starten, stop je huidige loop met /loop stop.");
            return;
        }

        ChatUtils.sendMessage(player, "<primary>Voert <secondary><command> <primary><amount> keer uit met <delay> tick delay.",
                Placeholder.unparsed("command", command),
                Placeholder.unparsed("amount", String.valueOf(amount)),
                Placeholder.unparsed("delay", String.valueOf(tickDelay))
        );

        if (command.startsWith("/")) command = command.replaceFirst("/", "");

        if (tickDelay == 0) {
            for (int i = 0; i <= amount; i++) {
                player.performCommand(command.replaceAll("%current%", String.valueOf(i)));
            }
            return;
        }

        LoopCommandTask loopTask = new LoopCommandTask(this, player, command, amount);
        loopTasks.put(player.getUniqueId(), loopTask);
        loopTask.runTaskTimer(EssentialsPlugin.instance(), 0L, tickDelay);
    }

    @Subcommand({"stop"})
    public void stopLoopCommand(Player player) {
        LoopCommandTask task = loopTasks.get(player.getUniqueId());
        if (task == null) {
            ChatUtils.sendMessage(player, "<danger>Je hebt momenteel geen loop");
            return;
        }

        task.cancel();
        loopTasks.remove(player.getUniqueId());
        player.sendMessage(ChatUtils.color("<primary>Je hebt je loop gestopt."));
    }

}
