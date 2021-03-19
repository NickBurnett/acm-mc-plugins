package com.acmutd.mc.core.module.core.command;

import com.acmutd.mc.api.ACMPlugin;
import com.acmutd.mc.api.command.Command;
import com.acmutd.mc.api.command.Permission;
import com.acmutd.mc.core.ACMCore;
import com.acmutd.mc.core.util.UtilityCache;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandAfk extends Command {
    public CommandAfk() {
        super(
                "afk",
                "Go AFK.",
                "/afk",
                new String[] {
                        "away"
                },
                new Permission("acm.core.afk")
        );
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.core.afk")) {
                    this.messagePermission(sender);
                    break;
                }
                final UtilityCache cache = ACMCore.get().getCache();
                if (cache.getAfk().contains(((Player) sender).getUniqueId())) {
                    cache.getAfk().remove(((Player) sender).getUniqueId());
                    ACMPlugin.getInstance().messager().message(sender, "You are no longer afk...");
                } else {
                    cache.getAfk().add(((Player) sender).getUniqueId());
                    ACMPlugin.getInstance().messager().message(sender, "You are now afk...");
                }
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
