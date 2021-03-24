package co.acmutd.mc.core.data;

import co.acmutd.mc.api.data.DataFileJSON;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.Location;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarpData extends DataFileJSON {
    public WarpData() {
        super(ACMCore.get().getDataFolder() + "/data/", "warps");
    }

    @Override
    public Map<String, Object> getDefaultData() {
        return new HashMap<>();
    }
    public final void addWarp(final String name, final Location loc) {
        final JSONObject data = new JSONObject();
        data.put("world", loc.getWorld().getUID().toString());
        data.put("x", loc.getBlockX());
        data.put("y", loc.getBlockY());
        data.put("z", loc.getBlockZ());
        data.put("yaw", loc.getYaw());
        data.put("pitch", loc.getPitch());
        this.getData().put(name, data);
    }
    public final boolean deleteWarp(final String name) {
        if (!this.getData().containsKey(name)) return false;
        this.getData().remove(name);
        return true;
    }
    public final Location getWarp(final String name) {
        if (!this.getData().containsKey(name)) return null;
        final JSONObject data = (JSONObject) this.getData().get(name);
        return new Location(ACMCore.get().getServer().getWorld(UUID.fromString((String) data.get("world"))), (int) data.get("x"), (int) data.get("y"), (int) data.get("z"), (float) data.get("yaw"), (float) data.get("pitch"));
    }
}
