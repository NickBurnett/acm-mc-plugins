package co.acmutd.mc.api.util;

import co.acmutd.mc.api.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UtilityMessager extends Utility {
    private final String prefix;
    public UtilityMessager(final String prefix) {
        this.prefix = prefix;
    }
    public final String getPrefix() {
        return this.prefix;
    }
    public final void messageRaw(final CommandSender user, final String raw, final boolean prefix, final String... vars) {
        String message = ChatColor.GRAY + raw;
        for (int i = 0; i < vars.length; i++) {
            message = message.replace("_i_".replace("i", "" + i), vars[i] + ChatColor.GRAY);
        }
        user.sendMessage(ChatColor.translateAlternateColorCodes('&', ((prefix) ? this.getPrefix() : "") + message));
    }
    public final void messageSpace(final CommandSender sender) {
        this.messageRaw(sender, "", false);
    }
    public final void message(final CommandSender user, final String raw, final String... vars) {
        this.messageRaw(user, raw, true, vars);
    }
    public final void messageUsage(final CommandSender user, final Command command) {
        this.message(user, ChatColor.GRAY + "Command Usage: _0_", ChatColor.RED + command.getUsage());
    }
    public final void messagePermission(final CommandSender user, final Command command) {
        this.message(user, ChatColor.RED + "Invalid permissions...");
    }
}
