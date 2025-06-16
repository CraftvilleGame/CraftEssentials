package nl.craftsmp.essentials.commands;

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import nl.craftsmp.essentials.annotation.AutoRegister;
import nl.craftsmp.essentials.utils.ChatUtils;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.bukkit.parameters.EntitySelector;

@AutoRegister
@Command({"heal"})
@Description("Heals you or someone else.")
public class HealCommand {

    @CommandPermission("craftessentials.command.heal")
    public void healCommand(Player player) {
        heal(player);

        ChatUtils.sendMessage(player, "<primary>Je hebt jezelf gehealed.");
    }

    @CommandPermission("craftessentials.command.heal.other")
    public void healCommandOther(CommandSender sender, EntitySelector<LivingEntity> target) {

        for (LivingEntity livingEntity : target) {
            heal(livingEntity);
        }

        String names = (target.size() == 1 ? target.getFirst().getName() : target.size() + " entities");
        ChatUtils.sendMessage(sender, "<primary>Je hebt <secondary><targets> <primary>gehealed.",
                Placeholder.unparsed("targets", names)
        );
    }


    private void heal(LivingEntity entity) {
        double maxHealth = 20;

        if (entity.getAttribute(Attribute.MAX_HEALTH) != null) {
            maxHealth = entity.getAttribute(Attribute.MAX_HEALTH).getValue();
        }

        if (entity instanceof Player player) {
            player.setFoodLevel(20);
            player.setFireTicks(0);
        }

        entity.setHealth(maxHealth);
    }

}
