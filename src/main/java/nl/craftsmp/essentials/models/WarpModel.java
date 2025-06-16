package nl.craftsmp.essentials.models;

import com.craftmend.storm.api.StormModel;
import com.craftmend.storm.api.markers.Column;
import com.craftmend.storm.api.markers.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.bukkit.Location;

@Data
@Accessors(fluent = true)
@Table(name = "warps")
@EqualsAndHashCode(callSuper = false)
public class WarpModel extends StormModel {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "location")
    private Location location;

}
