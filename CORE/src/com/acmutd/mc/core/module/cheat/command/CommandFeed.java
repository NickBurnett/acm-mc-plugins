package com.acmutd.mc.core.module.cheat.command;

import com.acmutd.mc.api.command.Command;
import com.acmutd.mc.api.command.Permission;
import com.acmutd.mc.core.ACMCore;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandFeed extends Command {
    public CommandFeed() {
        super(
                "feed",
                "Completely feeds a user.",
                "/feed ?<user>",
                new String[] {

                },
                new Permission("acm.cheat.feed.*").addChild("acm.cheat.feed").addChild("acm.cheat.feed.other"),
                new Permission("acm.cheat.feed"),
                new Permission("acm.cheat.feed.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        switch (args.length) {
            case 1:
                if (!sender.hasPermission("acm.cheat.feed.other")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[0]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ not found...", ChatColor.RED + args[0]);
                    break;
                }
                player.setFoodLevel(20);
                player.setSaturation(15f);
                ACMCore.getInstance().messager().message(sender, "Feeding _0_...", ChatColor.GREEN + player.getName());
                ACMCore.getInstance().messager().message(player, "Fully fed...");
                break;
            case 0:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.cheat.feed")) {
                    this.messagePermission(sender);
                    break;
                }
                player = (Player) sender;
                player.setFoodLevel(20);
                player.setSaturation(15f);
                ACMCore.getInstance().messager().message(player, "Fully fed...");
                break;
            default:
                this.messagePermission(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        final List<String> list = new ArrayList<>();
        switch (args.length) {
            case 1:
                for (final OfflinePlayer key : ACMCore.getInstance().getServer().getOfflinePlayers()) {
                    list.add(key.getName());
                }
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