package nl.craftsmp.essentials.managers;

import com.craftmend.storm.Storm;
import lombok.Getter;
import lombok.experimental.Accessors;
import nl.craftsmp.essentials.EssentialsPlugin;
import nl.craftsmp.essentials.database.Database;
import nl.craftsmp.essentials.models.WarpModel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Getter
@Accessors(fluent = true)
public class WarpManager {

    private final Set<WarpModel> warps = new HashSet<>();

    public void registerWarp(WarpModel warpModel) {
        Database database = EssentialsPlugin.instance().database();
        database.saveModel(warpModel);

        warps.add(warpModel);
    }

    public void loadWarps() {
        Database database = EssentialsPlugin.instance().database();
        Storm storm = database.storm();
        warps.clear();

        CompletableFuture.runAsync(() -> {
            try {
                Collection<WarpModel> models = storm.buildQuery(WarpModel.class)
                        .execute()
                        .join();

                warps.addAll(models);
                EssentialsPlugin.instance().getLogger().info("Loaded " + warps.size() + " warps!");
            } catch (Exception e) {
                EssentialsPlugin.instance().getLogger().severe("Couldn't load warp models: " + e.getMessage());
            }
        }, database.executorService());
    }

    public WarpModel getWarp(String name) {
        return warps.stream().filter(warp -> warp.name().equalsIgnoreCase(name))
                .findAny()
                .orElse(null);
    }

    public void deleteWarp(WarpModel warpModel) {
        Database database = EssentialsPlugin.instance().database();
        Storm storm = database.storm();
        warps.remove(warpModel);

        CompletableFuture.runAsync(() -> {
            try {
                storm.delete(warpModel);
                EssentialsPlugin.instance().getLogger().info("Loaded " + warps.size() + " warps!");
            } catch (Exception e) {
                EssentialsPlugin.instance().getLogger().severe("Couldn't remove warp model: " + e.getMessage());
            }
        }, database.executorService());
    }

}
