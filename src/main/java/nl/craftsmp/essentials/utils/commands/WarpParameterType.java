package nl.craftsmp.essentials.utils.commands;

import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.managers.WarpManager;
import nl.craftsmp.essentials.models.WarpModel;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.node.ExecutionContext;
import revxrsal.commands.parameter.ParameterType;
import revxrsal.commands.stream.MutableStringStream;

public class WarpParameterType implements ParameterType<BukkitCommandActor, WarpModel> {

    @Override
    public WarpModel parse(@NotNull MutableStringStream mutableStringStream, @NotNull ExecutionContext<@NotNull BukkitCommandActor> executionContext) {
        String name = mutableStringStream.readString();
        WarpManager warpManager = EssentialsPlugin.instance().warpManager();
        WarpModel warpModel = warpManager.getWarp(name);

        if (warpModel == null) throw new CommandErrorException("No warp for " + name);
        return warpModel;
    }
}
