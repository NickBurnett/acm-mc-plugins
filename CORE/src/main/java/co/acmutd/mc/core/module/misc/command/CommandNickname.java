package co.acmutd.mc.core.module.misc.command;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.UserData;
import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandNickname extends Command {
    public CommandNickname() {
        super(
                "nickname",
                "Changes the user\'s nickname.",
                "/nickname <nickname> ?<user>",
                new String[] {
                        "name",
                        "nick"
                },
                new Permission("acm.misc.nickname.*").addChild("acm.misc.nickname").addChild("acm.misc.nickname.other"),
                new Permission("acm.misc.nickname"),
                new Permission("acm.misc.nickname.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        String nickname;
        UserData data;
        switch (args.length) {
            case 2:
                if (!(sender.hasPermission("acm.misc.nickname.other"))) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[1]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ does not exist...", ChatColor.GREEN + args[1]);
                    return true;
                }
                data = ACMCore.get().getCache().getUsers().get(player.getUniqueId());
                nickname = args[0];
                data.setNickname(nickname);
                data.save();
                ACMCore.getInstance().messager().message(sender, "Set _0_'s nickname to _1_...", ChatColor.GREEN + player.getName(), ChatColor.translateAlternateColorCodes('&', nickname));
                break;
            case 1:
                if (!(sender instanceof Player) || !sender.hasPermission("acm.misc.nickname")) {
                    this.messageUsage(sender);
                    return true;
                }
                player = (Player) sender;
                data = ACMCore.get().getCache().getUsers().get(player.getUniqueId());
                nickname = args[0];
                data.setNickname(nickname);
                data.save();
                ACMCore.getInstance().messager().message(sender, "Set nickname to _0_...", ChatColor.translateAlternateColorCodes('&', nickname));
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        final List<String> toReturn = new ArrayList<>();
        switch (args.length) {
            case 2:
                for (final Player key : ACMCore.getInstance().getServer().getOnlinePlayers()) {
                    toReturn.add(key.getName());
                }
                break;
            default:
                break;
        }
        return toReturn;
    }
}
