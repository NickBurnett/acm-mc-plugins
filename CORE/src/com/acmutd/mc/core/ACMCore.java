package com.acmutd.mc.core;

import com.acmutd.mc.api.ACMPlugin;
import com.acmutd.mc.api.command.Command;
import com.acmutd.mc.api.command.Permission;
import com.acmutd.mc.api.data.DataFile;
import com.acmutd.mc.api.event.Listener;
import com.acmutd.mc.core.data.UserData;
import com.acmutd.mc.core.data.config.RankData;
import com.acmutd.mc.core.module.Module;
import com.acmutd.mc.core.module.cheat.ModuleCheat;
import com.acmutd.mc.core.module.core.ModuleCore;
import com.acmutd.mc.core.module.misc.ModuleMisc;
import com.acmutd.mc.core.module.permission.ModulePermission;
import com.acmutd.mc.core.util.UtilityCache;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class ACMCore extends ACMPlugin {
    private final Map<String, Module> modules;
    private final Map<String, DataFile> configs;
    public ACMCore() {
        super(new ACMPluginProperties("ACMCore", "0.0.0", "acm", "" + ChatColor.GOLD + ChatColor.BOLD + "ACM" + ChatColor.DARK_GRAY + ChatColor.BOLD + "» " + ChatColor.RESET));
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
    public final RankData getRankData() {
        return (RankData) this.configs.get("rank");
    }
    public final Map<String, Module> getModules() {
        return this.modules;
    }
    public final Map<String, DataFile> getConfigs() {
        return this.configs;
    }
    public final void refresh() {
        ACMCore.getInstance().logger().info("Refreshing configuration and data files...");
        for (final DataFile key : this.getConfigs().values()) {
            key.loadData();
        }
        for (final UserData key : this.getCache().getUsers().values()) {
            key.loadData();
            key.reloadPermissions();
        }
        ACMCore.getInstance().logger().info("Data refreshed...");
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
        for (final DataFile key : this.getConfigs().values()) {
            key.loadData();
        }
    }
}
