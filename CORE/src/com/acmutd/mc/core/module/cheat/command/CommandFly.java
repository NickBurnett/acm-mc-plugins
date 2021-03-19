package com.acmutd.mc.core.module.cheat.command;

import com.acmutd.mc.api.command.Command;
import com.acmutd.mc.api.command.Permission;
import com.acmutd.mc.core.ACMCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CommandFly extends Command {
    public CommandFly() {
        super(
                "fly",
                "Toggles a player's flight.",
                "/fly ?<user>",
                new String[] {
                        "flight"
                },
                new Permission("acm.cheat.fly.*").addChild("acm.cheat.fly").addChild("acm.cheat.fly.other"),
                new Permission("acm.cheat.fly"),
                new Permission("acm.cheat.fly.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        switch (args.length) {
            case 1:
                if (!sender.hasPermission("acm.cheat.fly.other")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[0]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ not found...", ChatColor.RED + args[0]);
                    break;
                }
                ACMCore.getInstance().messager().message(player, "Toggling _0_...", ChatColor.GREEN + "flight");
                player.setAllowFlight(!player.getAllowFlight());
                break;
            case 0:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.cheat.fly")) {
                    this.messagePermission(sender);
                    break;
                }
                player = (Player) sender;
                ACMCore.getInstance().messager().message(player, "Toggling _0_...", ChatColor.GREEN + "flight");
                player.setAllowFlight(!player.getAllowFlight());
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        final List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                for (final Player key : ACMCore.getInstance().getServer().getOnlinePlayers()) {
                    list.add(key.getName());
                }
                break;
            default:
                break;
        }
        return list;
    }
}
