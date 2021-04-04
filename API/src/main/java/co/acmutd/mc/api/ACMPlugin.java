package co.acmutd.mc.api;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.api.event.Listener;
import co.acmutd.mc.api.util.UtilityMessager;
import co.acmutd.mc.api.util.Utility;
import co.acmutd.mc.api.util.UtilityLogger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class ACMPlugin extends JavaPlugin {
    private static ACMPlugin INSTANCE = null;
    private final ACMPluginProperties properties;
    private final HashMap<String, Utility> pluginUtils;
    private final HashMap<String, Command> pluginCommands;
    private final HashMap<String, Permission> pluginPermissions;
    private final HashMap<Class<? extends Listener>, Listener> pluginListeners;
    private final HashMap<String, BukkitRunnable> tasks;
    public ACMPlugin(final ACMPluginProperties properties) {
        ACMPlugin.INSTANCE = this;
        this.properties = properties;
        this.pluginUtils = new HashMap<>();
        this.pluginCommands = new HashMap<>();
        this.pluginPermissions = new HashMap<>();
        this.pluginListeners = new HashMap<>();
        this.tasks = new HashMap<>();
        this.initialize();
    }
    public static ACMPlugin getInstance() {
        return ACMPlugin.INSTANCE;
    }
    public void initialize() {
        this.getUtilities().put("logger", new UtilityLogger());
        this.getUtilities().put("messager", new UtilityMessager(this.getProperties().getPrefix()));
    }
    public final ACMPluginProperties getProperties() {
        return this.properties;
    }
    public final HashMap<String, Utility> getUtilities() {
        return this.pluginUtils;
    }
    public final HashMap<String, Command> getCommands() {
        return this.pluginCommands;
    }
    public final HashMap<String, Permission> getPermissions() {
        return this.pluginPermissions;
    }
    public final HashMap<Class<? extends Listener>, Listener> getListeners() {
        return this.pluginListeners;
    }
    public final HashMap<String, BukkitRunnable> getTasks() {
        return this.tasks;
    }
    public final UtilityLogger logger() {
        return (UtilityLogger) this.getUtilities().get("logger");
    }
    public final UtilityMessager messager() {
        return (UtilityMessager) this.getUtilities().get("messager");
    }
    public final CommandMap getCommandMap() {
        try {
            final Field map = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            map.setAccessible(true);
            return (CommandMap) map.get(Bukkit.getServer().getPluginManager());
        } catch (final NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
            this.logger().err("CommandMap initialization failed...");
        }
        return null;
    }
    @Override
    public final void onEnable() {
        super.onEnable();
        this.registerCommands();
        this.registerPermissions();
        this.registerListeners();
    }
    @Override
    public final void onDisable() {
        super.onDisable();
    }

    private void registerCommands() {
        this.logger().info("Registering Commands...");
        if (this.getCommandMap() == null) return;
        for (final Command key : this.getCommands().values()) {
            final org.bukkit.command.Command command = key.build();
            this.getCommandMap().register(key.getName(), command);
            if (key.getPermissions() == null) continue;
            for (final Permission perm : key.getPermissions()) {
                this.addPermission(perm);
            }
        }
    }
    private void registerPermissions() {
        this.logger().info("Registering Permissions...");
        for (final Permission key : this.getPermissions().values()) {
            this.getServer().getPluginManager().addPermission(key.build());
        }
    }
    private void registerListeners() {
        this.logger().info("Registering Listeners...");
        for (final Listener key : this.getListeners().values()) {
            this.getServer().getPluginManager().registerEvents(key, this);
        }
    }
    protected final boolean addCommand(final Command command) {
        if (this.getCommands().containsKey(command.getName())) return false;
        this.getCommands().put(command.getName(), command);
        return true;
    }
    protected final boolean addPermission(final Permission permission) {
        if (this.getPermissions().containsKey(permission.getNode())) return false;
        this.getPermissions().put(permission.getNode(), permission);
        return true;
    }
    protected final boolean addWildcardPermission(final String base, final Permission... children) {
        final Permission perm = new Permission(base);
        for (final Permission key : children) {
            perm.addChild(key.getNode());
        }
        this.getPermissions().put(perm.getNode(), perm);
        return true;
    }
    protected final boolean addListener(final Listener listener) {
        if (this.getListeners().containsKey(listener.getClass())) return false;
        this.getListeners().put(listener.getClass(), listener);
        return true;
    }
    protected final boolean addTask(final String key, final BukkitRunnable callback, final long delay, final long period) {
        if (this.getTasks().containsKey(key)) return false;
        this.getTasks().put(key, callback);
        callback.runTaskTimer(this, delay, period);
        return true;
    }
    public static class ACMPluginProperties {
        protected final String name;
        protected final String version;
        protected final String permissionBase;
        protected final String prefix;
        public ACMPluginProperties(final String name, final String version, final String permissionBase, final String prefix) {
            this.name = name;
            this.version = version;
            this.permissionBase = permissionBase;
            this.prefix = prefix;
        }
        public final String getPrefix() {
            return this.prefix;
        }
        public final String asPermission(final String key) {
            return this.permissionBase + '.' + key;
        }
    }
}
