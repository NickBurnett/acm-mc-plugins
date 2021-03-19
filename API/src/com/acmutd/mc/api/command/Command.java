package com.acmutd.mc.api.command;

import com.acmutd.mc.api.ACMPlugin;
import org.bukkit.command.*;

import java.util.Arrays;
import java.util.List;

public abstract class Command {
    private final String name;
    private final String description;
    private final String usage;
    private final List<String> aliases;
    private final List<Permission> permissions;
    public Command(final String name, final String description, final String usage, final String[] aliases, final Permission... permissions) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.aliases = Arrays.asList(aliases);
        this.permissions = Arrays.asList(permissions);
    }
    protected final void messageUsage(final CommandSender sender) {
        ACMPlugin.getInstance().messager().messageUsage(sender, this);
    }
    protected final void messagePermission(final CommandSender sender) {
        ACMPlugin.getInstance().messager().messagePermission(sender, this);
    }
    public final String getName() {
        return this.name;
    }
    public final String getDescription() {
        return this.description;
    }
    public final String getUsage() {
        return this.usage;
    }
    public final List<String> getAliases() {
        return this.aliases;
    }
    public final List<Permission> getPermissions() {
        return this.permissions;
    }
    public final org.bukkit.command.Command build() {
        return new org.bukkit.command.Command(this.getName(), this.getDescription(), this.getUsage(), this.getAliases()) {
            @Override
            public boolean execute(final CommandSender sender, final String label, final String[] args) {
                return onCommand(sender, this, label, args);
            }

            @Override
            public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
                return onTabComplete(sender, this, alias, args);
            }
        };
    }
    public final CommandExecutor buildExecutor() {
        return new CommandExecutor() {
            @Override
            public boolean onCommand(final CommandSender sender, org.bukkit.command.Command command, final String label, final String[] args) {
                return Command.this.onCommand(sender, command, label, args);
            }
        };
    }
    public final TabCompleter buildTabCompleter() {
        return new TabCompleter() {
            @Override
            public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
                return Command.this.onTabComplete(sender, command, label, args);
            }
        };
    }


    public abstract boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args);
    public abstract List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args);
}
