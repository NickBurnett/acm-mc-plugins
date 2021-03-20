package co.acmutd.mc.core.data.config;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.api.data.DataFileJSON;
import org.json.simple.JSONObject;

import java.util.*;

public class RankData extends DataFileJSON {
    public RankData() {
        super(ACMCore.get().getDataFolder() + "/config/", "ranks");
    }
    @Override
    public final Map<String, Object> getDefaultData() {
        final Map<String, Object> data = new HashMap<>();
        final JSONObject defaultRank = new JSONObject();
        {
            defaultRank.put("prefix", "&7&lDEFAULT");
            defaultRank.put("permissions", new ArrayList<String>());
            defaultRank.put("children", new ArrayList<String>());
        }
        data.put("default_rank", "default");
        data.put("default", defaultRank);
        return data;
    }
    public final void addRank(final String name) {
        final Rank rank = new Rank(name);
        this.saveRank(rank);
    }
    public final Rank getRank(final String name) {
        if (!this.getData().containsKey(name) || name.equals("default_rank")) return null;
        JSONObject data = (JSONObject) this.getData().get(name);
        return new Rank(name, (String) data.get("prefix"), (ArrayList<String>) data.get("permissions"), (ArrayList<String>) data.get("children"));
    }
    public final void saveRank(final Rank rank) {
        final JSONObject data = new JSONObject();
        rank.getPermissions().sort(null);
        rank.getChildren().sort(null);
        data.put("prefix", rank.getPrefix());
        data.put("permissions", rank.getPermissions());
        data.put("children", rank.getChildren());
        this.getData().put(rank.getName(), data);
        this.save();
    }
    public final boolean deleteRank(final Rank rank) {
        if (!this.getData().containsKey(rank.getName())) return false;
        this.getData().remove(rank.getName());
        this.save();
        return true;
    }
    public final Rank getDefaultRank() {
        return this.getRank((String) this.getData().get("default_rank"));
    }
    public final List<String> getRankPermissions(final Rank rank) {
        final List<String> perms = new ArrayList<>();
        final Set<String> set = new HashSet<>();
        final ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add(rank.getName());
        set.add(rank.getName());
        while (!queue.isEmpty()) {
            final String s = queue.poll();
            final Rank r = this.getRank(s);
            if (r == null) continue;
            for (final String key : r.getChildren()) {
                if (set.contains(key)) continue;
                set.add(key);
                queue.add(key);
            }
            perms.addAll(r.getPermissions());
        }
        return perms;
    }
    public static class Rank {
        private final String name;
        private String prefix;
        private final List<String> permissions;
        private final List<String> children;
        public Rank(final String name) {
            this(name, name, new ArrayList<>(), new ArrayList<>());
        }
        public Rank(final String name, final String prefix, final List<String> permissions, final List<String> children) {
            this.name = name;
            this.prefix = prefix;
            this.permissions = permissions;
            this.children = children;
        }
        public final String getName() {
            return this.name;
        }
        public final String getPrefix() {
            return this.prefix;
        }
        public final void setPrefix(final String prefix) {
            this.prefix = prefix;
        }
        public final List<String> getPermissions() {
            return this.permissions;
        }
        public final List<String> getChildren() {
            return this.children;
        }
    }
}
