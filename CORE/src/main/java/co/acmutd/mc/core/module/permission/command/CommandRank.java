package co.acmutd.mc.core.module.permission.command;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.misc.wizard.rank.RankWizard;
import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.core.data.config.RankData;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandRank extends Command {
    public CommandRank() {
        super(
                "rank",
                "Access the permission system.",
                "/rank <rank> ?wizard",
                new String[] {
                        "group"
                },
                new Permission("acm.permission.rank.*").addChild("acm.permission.rank")/*.addChild("acm.permission.rank.wizard").addChild("acm.permission.rank.view")*/,
                new Permission("acm.permission.rank").addChild("acm.permission.rank.wizard").addChild("acm.permission.rank.view").addChild("acm.permission.rank.set"),
                new Permission("acm.permission.rank.wizard"),
                new Permission("acm.permission.rank.view")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        RankData.Rank rank;
        switch (args.length) {
            case 3:
            case 2:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.permission.rank.wizard")) {
                    this.messagePermission(sender);
                    break;
                }
                final Player player = (Player) sender;
                rank = ACMCore.get().getRankData().getRank(args[0]);
                if (rank == null) {
                    ACMCore.getInstance().messager().message(sender, "Invalid rank _0_, creating it...", ChatColor.RED + args[0]);
                    ACMCore.get().getRankData().addRank(args[0]);
                    rank = ACMCore.get().getRankData().getRank(args[0]);
                }
                if (!args[1].equals("wizard")) {
                    this.messageUsage(sender);
                    break;
                }
                final RankWizard wizard = new RankWizard(player.getUniqueId(), args.length == 3 && args[2].equals("focus"), rank);
                wizard.enterWizard();

                break;
            case 1:
                if (!sender.hasPermission("acm.permission.rank.view")) {
                    this.messagePermission(sender);
                    break;
                }
                rank = ACMCore.get().getRankData().getRank(args[0]);
                if (rank == null) {
                    ACMCore.getInstance().messager().message(sender, "Invalid rank _0_...", ChatColor.RED + args[0]);
                    break;
                }
                // send the user the rank data
                ACMCore.getInstance().messager().messageSpace(sender);
                ACMCore.getInstance().messager().messageRaw(sender, "&7&lName&8&l: &7&l_0_", false, rank.getName());
                ACMCore.getInstance().messager().messageRaw(sender, "&7&lPrefix&8&l: _0_", false, rank.getPrefix());
                ACMCore.getInstance().messager().messageRaw(sender, "&7&lPermissions&8&l:", false);
                for (final String key : rank.getPermissions()) {
                    ACMCore.getInstance().messager().messageRaw(sender, "&6_0_", false, key);
                }
                ACMCore.getInstance().messager().messageRaw(sender, "&7&lChildren&8&l:", false);
                for (final String key : rank.getChildren()) {
                    ACMCore.getInstance().messager().messageRaw(sender, "&6_0_", false, key);
                }
                ACMCore.getInstance().messager().messageSpace(sender);
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
                final Set<String> ranks = ACMCore.get().getRankData().getData().keySet();
                ranks.remove("default_rank");
                list.addAll(ranks);
                break;
            case 2:
                list.add("wizard");
                break;
            case 3:
                list.add("focus");
                break;
        }
        return list;
    }
}
