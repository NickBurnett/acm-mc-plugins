package co.acmutd.mc.api.misc;

import co.acmutd.mc.api.ACMPlugin;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Team;

public abstract class Scoreboard {
    private org.bukkit.scoreboard.Scoreboard scoreboard;
    private ScoreboardEntry[] entries;

    public Scoreboard(final String title) {
        this.scoreboard = ACMPlugin.getInstance().getServer().getScoreboardManager().getNewScoreboard();
        this.entries = new ScoreboardEntry[15];
        this.scoreboard.registerNewObjective("main", "dummy", ChatColor.translateAlternateColorCodes('&', (title.length() > 16) ? title.substring(0, 16) : title));
        this.scoreboard.getObjective("main").setDisplaySlot(DisplaySlot.SIDEBAR);
        for (int i = 0; i < ChatColor.values().length && i < this.entries.length; i++) {
            this.entries[i] = new ScoreboardEntry(ChatColor.values()[i].toString(), "", i);
        }
    }
    public final org.bukkit.scoreboard.Scoreboard getScoreboard() {
        return this.scoreboard;
    }
    public final ScoreboardEntry[] getEntries() {
        return this.entries;
    }
    public final void addEntry(final int slot, final String display) {
        this.getScoreboard().getObjective("main").getScore(this.getEntries()[slot].getKey()).setScore(this.getEntries()[slot].getScore());
        this.getEntries()[slot].setDisplay(display);
        this.getEntries()[slot].buildTeam();
    }
    public final void removeEntry(final int slot) {
        this.getScoreboard().resetScores(this.getEntries()[slot].getKey());
        this.getEntries()[slot] = new ScoreboardEntry(this.getEntries()[slot].getKey(), "", this.getEntries()[slot].getScore());
    }
    public abstract void update();

    public class ScoreboardEntry {
        private final String key;
        private String display;
        private final int score;
        private Team team;
        public ScoreboardEntry(final String key, final String display, final int score) {
            this.key = key;
            this.display = display;
            this.score = score;
            this.team = null;
        }
        public final String getKey() {
            return this.key;
        }
        public final String getDisplay() {
            return this.display;
        }
        public final void setDisplay(final String display) {
            this.display = display;
        }
        public final int getScore() {
            return this.score;
        }
        public final Team getTeam() {
            return this.team;
        }
        public final Team buildTeam() {
            this.team = (scoreboard.getTeam(this.getKey()) == null) ? scoreboard.registerNewTeam(this.getKey()) : scoreboard.getTeam(this.getKey());
            String prefix = this.getDisplay();
            String suffix = "";
            if (this.getDisplay().length() > 16) {
                prefix = this.getDisplay().substring(0, 16);
                suffix = this.getDisplay().substring(16);
                suffix = (suffix.length() > 16) ? suffix.substring(16, 32) : suffix;
            } else {
                prefix = this.getDisplay();
            }
            this.getTeam().setPrefix(ChatColor.translateAlternateColorCodes('&', prefix));
            this.getTeam().setSuffix(ChatColor.translateAlternateColorCodes('&', ChatColor.getLastColors(this.getTeam().getPrefix()) + suffix));
            this.getTeam().addEntry(this.getKey());
            return this.getTeam();
        }
    }
}
