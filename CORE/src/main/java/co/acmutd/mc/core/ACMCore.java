package co.acmutd.mc.core;

import co.acmutd.mc.core.data.WarpData;
import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.core.ModuleCore;
import co.acmutd.mc.core.module.transportation.ModuleTransportation;
import co.acmutd.mc.core.util.UtilityCache;
import co.acmutd.mc.api.ACMPlugin;
import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.api.data.DataFile;
import co.acmutd.mc.api.event.Listener;
import co.acmutd.mc.core.data.UserData;
import co.acmutd.mc.core.data.RankData;
import co.acmutd.mc.core.module.cheat.ModuleCheat;
import co.acmutd.mc.core.module.misc.ModuleMisc;
import co.acmutd.mc.core.module.permission.ModulePermission;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ACMCore extends ACMPlugin {
    private final Map<String, Module> modules;
    private final Map<String, DataFile> configs;
    public ACMCore() {
        super(new ACMPluginProperties("ACM", "1.0.0", "acm", "" + ChatColor.GOLD + ChatColor.BOLD + "ACM " + ChatColor.DARK_GRAY + ChatColor.BOLD + "\u00BB " + ChatColor.RESET));
        this.modules = new HashMap<>();
        this.configs = new HashMap<>();
        this.initializeUtilities();
        this.initializeModules();
        this.initializeConfigs();
    }
    public static ACMCore get() {
        return (ACMCore) ACMCore.getInstance();
    }
    public final UtilityCache getCache() {
        return (UtilityCache) this.getUtilities().get("cache");
    }
    public final Map<String, Module> getModules() {
        return this.modules;
    }
    public final Map<String, DataFile> getConfigs() {
        return this.configs;
    }
    public final RankData getRankData() {
        return (RankData) this.configs.get("rank");
    }
    public final WarpData getWarpData() {
        return (WarpData) this.getConfigs().get("warp");
    }

    public final void refresh() {
        ACMCore.getInstance().logger().info("Refreshing configuration and data files...");
        for (final DataFile key : this.getConfigs().values()) {
            key.loadData();
        }
        for (final UserData key : this.getCache().getUsers().values()) {
            key.loadData();
            key.reloadPermissions();
            this.refreshScoreboard(key.getUniqueId());
        }
        ACMCore.getInstance().logger().info("Data refreshed...");
    }
    public final boolean refreshScoreboard(final UUID uuid) {
        if (!this.getCache().getScoreboards().containsKey(uuid)) return false;
        this.getCache().getScoreboards().get(uuid).update();
        return true;
    }
    private void addModule(final Module module) {
        this.getModules().put(module.getName(), module);
    }
    private void initializeUtilities() {
        this.getUtilities().put("cache", new UtilityCache());
    }
    private void initializeModules() {
        this.addModule(new ModuleCore());
        this.addModule(new ModulePermission());
        this.addModule(new ModuleTransportation());
        this.addModule(new ModuleCheat());
        this.addModule(new ModuleMisc());
        final Permission global = new Permission("acm.*");
        for (final Module key : this.getModules().values()) {
            global.addChild(key.getPermissionBase() + ".*");
            for (final Command cmd : key.getCommands().values()) this.addCommand(cmd);
            for (final Permission perm : key.getPermissions().values()) this.addPermission(perm);
            for (final Listener listener : key.getListeners().values()) this.addListener(listener);
        }
        this.addPermission(global);
    }
    private void initializeConfigs() {
        this.getConfigs().put("rank", new RankData());
        this.getConfigs().put("warp", new WarpData());
        for (final DataFile key : this.getConfigs().values()) {
            key.loadData();
        }
    }
}
