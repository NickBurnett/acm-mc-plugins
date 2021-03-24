package co.acmutd.mc.core.module.transportation.command;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandWarp extends Command {
    public CommandWarp() {
        super(
                "warp",
                "Warp to a specified location.",
                "/warp <name> ?<user>",
                new String[] {
                        "tpw"
                },
                new Permission("acm.transport.warp.*").addChild("acm.transport.warp").addChild("acm.transport.warp.other"),
                new Permission("acm.transport.warp"),
                new Permission("acm.transport.warp.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player = null;
        switch (args.length) {
            case 2:
                if (!sender.hasPermission("acm.transport.warp") || !sender.hasPermission("acm.transport.warp.other")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.get().getServer().getPlayer(args[1]);
                if (player == null) {
                    ACMCore.get().messager().message(sender, ChatColor.RED + "Invalid player...");
                    break;
                }
            case 1:
                if (!sender.hasPermission("acm.transport.warp")) {
                    this.messagePermission(sender);
                    break;
                }
                final Location warp = ACMCore.get().getWarpData().getWarp(args[0].toLowerCase());
                if (warp == null) {
                    ACMCore.get().messager().message(sender, ChatColor.RED + "Invalid warp _0_...", args[0].toLowerCase());
                    break;
                }
                if (player == null) {
                    player = (Player) sender;
                }
                player.teleport(warp);
                ACMCore.get().messager().message(sender, "Warping to _0_...", ChatColor.GREEN + args[0].toLowerCase());
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        final List<String>  list = new ArrayList<>();
        switch (args.length) {
            case 2:
                for (final Player key : ACMCore.getInstance().getServer().getOnlinePlayers()) {
                    list.add(key.getName());
                }
                break;
            case 1:
                final Set<String> warps = ACMCore.get().getWarpData().getData().keySet();
                list.addAll(warps);
                break;
            default:
                break;
        }
        return list;
    }
}
