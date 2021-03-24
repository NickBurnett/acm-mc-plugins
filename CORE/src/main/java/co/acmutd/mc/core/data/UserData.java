package co.acmutd.mc.core.data;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.api.data.DataFileJSON;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserData extends DataFileJSON {
    private final UUID uuid;
    public UserData(final UUID uuid) {
        super(ACMCore.get().getDataFolder() + "/user/", uuid.toString());
        this.uuid = uuid;
    }
    public final UUID getUniqueId() {
        return uuid;
    }
    public final RankData.Rank getRank() {
        RankData.Rank rank = ACMCore.get().getRankData().getRank((String) this.getData().get("rank"));
        if (rank == null) {
            rank = ACMCore.get().getRankData().getDefaultRank();
            this.setRank(rank);
            this.save();
        }
        return rank;
    }
    public final void setRank(final RankData.Rank rank) {
        this.getData().put("rank", rank.getName());
    }
    public final String getNickname() {
        return (String) this.getData().get("nickname");
    }
    public final void setNickname(final String nickname) {
        this.getData().put("nickname", nickname);
    }
    public final void reloadPermissions() {
        final Player player = ACMCore.getInstance().getServer().getPlayer(this.getUniqueId());
        final PermissionAttachment old = ACMCore.get().getCache().getPermissions().remove(this.getUniqueId());
        if (old != null) old.remove();
        final PermissionAttachment perms = player.addAttachment(ACMCore.getInstance());
        for (final String key : ACMCore.get().getRankData().getRankPermissions(this.getRank())) {
            perms.setPermission(key, true);
        }
        player.recalculatePermissions();
        player.updateCommands();
        ACMCore.get().getCache().getPermissions().put(this.getUniqueId(), perms);
    }

    @Override
    public Map<String, Object> getDefaultData() {
        final Map<String, Object> data = new HashMap<>();
        data.put("rank", "default");
        data.put("nickname", "");
        return data;
    }
}
