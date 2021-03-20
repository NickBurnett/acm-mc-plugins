package co.acmutd.mc.core.module;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.api.event.Listener;

import java.util.HashMap;
import java.util.Map;

public abstract class Module {
    private final String name;
    private final String permBase;
    private final Map<String, Command> commands;
    private final Map<String, Permission> permissions;
    private final Map<Class<? extends Listener>, Listener> listeners;
    public Module(final String name, final String permBase) {
        this.name = name;
        this.permBase = permBase;
        this.commands = new HashMap<>();
        this.permissions = new HashMap<>();
        this.listeners = new HashMap<>();
    }
    public final String getName() {
        return this.name;
    }
    public final String getPermissionBase() {
        return this.permBase;
    }
    public final Map<String, Command> getCommands() {
        return this.commands;
    }
    public final Map<String, Permission> getPermissions() {
        return this.permissions;
    }
    public final Map<Class<? extends Listener>, Listener> getListeners() {
        return this.listeners;
    }
    public final boolean addCommand(final Command command) {
        if (this.getCommands().containsKey(command.getName())) return false;
        this.getCommands().put(command.getName(), command);
        return true;
    }
    public final boolean addPermission(final Permission permission) {
        if (this.getPermissions().containsKey(permission.getNode())) return false;
        this.getPermissions().put(permission.getNode(), permission);
        return true;
    }
    public final boolean addWildcardPermission() {
        final Permission perm = new Permission(this.permBase + ".*");
        for (final Command key : this.getCommands().values()) {
            for (final Permission p : key.getPermissions()) {
                perm.addChild(p.getNode());
            }
        }
        this.getPermissions().put(perm.getNode(), perm);
        return true;
    }
    public final boolean addListener(final Listener listener) {
        if (this.getListeners().containsKey(listener.getClass())) return false;
        this.getListeners().put(listener.getClass(), listener);
        return true;
    }
}
