package co.acmutd.mc.core.misc.scoreboard;

import co.acmutd.mc.api.misc.Scoreboard;
import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.UserData;
import org.bukkit.ChatColor;

import java.util.UUID;

public class ScoreboardCore extends Scoreboard {
    private final UUID uuid;

    public ScoreboardCore(final String title, final UUID uuid) {
        super(title);
        this.uuid = uuid;
    }

    @Override
    public void update() {
        final UserData data = ACMCore.get().getCache().getUsers().get(this.uuid);
        if (data == null) return;
        this.addEntry(2, "");
        this.addEntry(1, "&e&lRANK");
        this.addEntry(0, data.getRank().getPrefix());
        this.getScoreboard().getObjective("main").setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lACM&7&lUTD"));
    }
}
