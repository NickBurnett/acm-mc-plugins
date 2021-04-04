package co.acmutd.mc.core.module.permission.command;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.UserData;
import co.acmutd.mc.core.data.RankData;
import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandRankSet extends Command {
    public CommandRankSet() {
        super(
                "setrank",
                "Sets a user's rank.",
                "/setrank <user> <rank>",
                new String[] {
                        "rankset"
                },
                new Permission("acm.permission.rank.set")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        UserData data;
        switch (args.length) {
            case 2:
                if (!sender.hasPermission("acm.permission.rank.set")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[0]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ does not exist...", ChatColor.GREEN + args[0]);
                    return true;
                }
                data = ACMCore.get().getCache().getUsers().get(player.getUniqueId());
                final RankData.Rank rank = ACMCore.get().getRankData().getRank(args[1]);
                if (rank == null) {
                    ACMCore.getInstance().messager().message(sender, "Invalid rank _0_...", ChatColor.RED + args[1]);
                    break;
                }
                data.setRank(rank);
                data.reloadPermissions();
                data.save();
                ACMCore.getInstance().messager().message(sender, "Setting &a_0_'s rank to &a_1_...", player.getName(), rank.getName());
                ACMCore.getInstance().messager().message(player, "Rank set to &a_0_...", rank.getName());
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
            case 2:
                final Set<String> ranks = ACMCore.get().getRankData().getData().keySet();
                ranks.remove("default_rank");
                list.addAll(ranks);
                break;
            default:
                break;
        }
        return list;
    }
}
