package nl.craftsmp.essentials.utils.commands;

import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.managers.WarpManager;
import nl.craftsmp.essentials.models.WarpModel;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.node.ExecutionContext;

import java.util.Collection;

public class WarpSuggestionProvider implements SuggestionProvider<BukkitCommandActor> {

    @Override
    public @NotNull Collection<String> getSuggestions(@NotNull ExecutionContext<BukkitCommandActor> executionContext) {
        WarpManager warpManager = EssentialsPlugin.instance().warpManager();

        return warpManager.warps().stream()
                .map(WarpModel::name)
                .toList();
    }

}
