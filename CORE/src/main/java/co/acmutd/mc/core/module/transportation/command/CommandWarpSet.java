package co.acmutd.mc.core.module.transportation.command;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandWarpSet extends Command {
    public CommandWarpSet() {
        super(
                "setwarp",
                "Sets a warp at the user's current location.",
                "/setwarp <name>",
                new String[] {
                        "warpset"
                },
                new Permission("acm.transport.setwarp")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        switch (args.length) {
            case 1:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.transport.setwarp")) {
                    this.messagePermission(sender);
                    break;
                }
                ACMCore.get().getWarpData().addWarp(args[0].toLowerCase(), ((Player) sender).getLocation());
                ACMCore.getInstance().messager().message(sender, "Set warp _0_ to your current location...", ChatColor.GREEN + args[0].toLowerCase());
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        return new ArrayList<>();
    }
}
