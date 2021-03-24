package co.acmutd.mc.core.module.transportation.command;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandWarpDelete extends Command {
    public CommandWarpDelete() {
        super(
                "delwarp",
                "Removes a given warp.",
                "/delwarp <name>",
                new String[] {
                        "removewarp"
                },
                new Permission("acm.transport.delwarp")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.transport.delwarp")) {
                    this.messagePermission(sender);
                    break;
                }
                if (!ACMCore.get().getWarpData().deleteWarp(args[0].toLowerCase())) {
                    ACMCore.getInstance().messager().message(sender, ChatColor.RED + "Invalid warp _0_...", args[0].toLowerCase());
                    break;
                }
                ACMCore.get().getWarpData().save();
                ACMCore.getInstance().messager().message(sender, "Removed warp _0_...", ChatColor.GREEN + args[0].toLowerCase());
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
                final Set<String> warps = ACMCore.get().getWarpData().getData().keySet();
                list.addAll(warps);
                break;
        }
        return list;
    }
}
