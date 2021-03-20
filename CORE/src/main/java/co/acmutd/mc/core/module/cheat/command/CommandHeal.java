package co.acmutd.mc.core.module.cheat.command;
import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHeal extends Command {
    public CommandHeal() {
        super(
                "heal",
                "Completely heals a user.",
                "/heal ?<user>",
                new String[] {

                },
                new Permission("acm.cheat.heal.*").addChild("acm.cheat.heal").addChild("acm.cheat.heal.other"),
                new Permission("acm.cheat.heal"),
                new Permission("acm.cheat.heal.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        switch (args.length) {
            case 1:
                if (!sender.hasPermission("acm.cheat.heal.other")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[0]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ not found...", ChatColor.RED + args[0]);
                    break;
                }
                player.setHealth(player.getHealthScale());
                player.setFoodLevel(20);
                player.setSaturation(15f);
                ACMCore.getInstance().messager().message(sender, "Healing _0_...", ChatColor.GREEN + player.getName());
                ACMCore.getInstance().messager().message(player, "Fully healed...");
                break;
            case 0:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.cheat.heal")) {
                    this.messagePermission(sender);
                    break;
                }
                player = (Player) sender;
                player.setHealth(player.getHealthScale());
                player.setFoodLevel(20);
                player.setSaturation(15f);
                ACMCore.getInstance().messager().message(player, "Fully healed...");
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