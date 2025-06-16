package nl.craftsmp.essentials.database.adapters;

import com.craftmend.storm.Storm;
import com.craftmend.storm.parser.objects.ParsedField;
import com.craftmend.storm.parser.types.objects.StormTypeAdapter;
import nl.craftsmp.essentials.utils.LocationUtils;
import org.bukkit.Location;

public class LocationAdapter extends StormTypeAdapter<Location> {

    @Override
    public Location fromSql(ParsedField parsedField, Object value) {
        if (value == null) return null;
        String location = value.toString();
        return LocationUtils.stringToLocation(location);
    }

    @Override
    public Object toSql(Storm storm, Location location) {
        return LocationUtils.locationToString(location);
    }

    @Override
    public String getSqlBaseType() {
        return "VARCHAR(%max)";
    }

    @Override
    public boolean escapeAsString() {
        return true;
    }

}
