package nl.craftsmp.essentials.tasks;

import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBarViewer;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.commands.LoopCommand;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class LoopCommandTask extends BukkitRunnable {

    private LoopCommand loopCommand;
    public Player player;
    public String command;
    private int amount;
    private int currentAmount;
    private BossBar bossBar;

    public LoopCommandTask(LoopCommand loopCommand, Player player, String command, int amount) {
        this.loopCommand = loopCommand;
        this.player = player;
        this.command = command;
        this.amount = amount;
        this.currentAmount = 0;
        this.bossBar = BossBar.bossBar(ChatUtils.color("<white>Looping command <gray>\"<command>\"<white>. <white>Progress: <gray>0<dark_gray>/<gray><amount> <dark_gray>(<gray>0%<dark_gray>)",
                Placeholder.unparsed("command", command),
                Placeholder.unparsed("amount", String.valueOf(amount))
                ), 0, BossBar.Color.WHITE, BossBar.Overlay.PROGRESS);
        player.showBossBar(bossBar);
    }

    @Override
    public void run() {
        currentAmount++;
        updateBar();
        player.performCommand(command.replaceAll("%current%", String.valueOf(currentAmount)));
        if (currentAmount >= amount) {
            cancel();
        }
    }

    @Override
    public void cancel() {
        loopCommand.loopTasks().remove(player.getUniqueId());
        player.hideBossBar(bossBar);
        bossBar = null;
        Bukkit.getScheduler().cancelTask(getTaskId());
    }

    public void updateBar() {
        float value = (float) currentAmount / amount;
        float percentage = (currentAmount * 100.0f) / amount;
        bossBar.progress(value);
        bossBar.name(ChatUtils.color("<white>Looping command <gray>\"<command>\"<white>. <white>Progress: <gray><current_amount><dark_gray>/<gray><max_amount> <dark_gray>(<gray><percentage>%<dark_gray>)",
                Placeholder.unparsed("command", command),
                Placeholder.unparsed("current_amount", String.valueOf(currentAmount)),
                Placeholder.unparsed("max_amount", String.valueOf(amount)),
                Placeholder.unparsed("percentage", String.valueOf(percentage))
        ));
    }

}
