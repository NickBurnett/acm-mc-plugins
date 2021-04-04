package co.acmutd.mc.core.module.cheat.command;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpeed extends Command {
    public CommandSpeed() {
        super(
                "heal",
                "Changes the user's speed.",
                "/speed <0-10> <walk/fly> ?<user>",
                new String[] {

                },
                new Permission("acm.cheat.speed.*").addChild("acm.cheat.speed").addChild("acm.cheat.speed.other"),
                new Permission("acm.cheat.speed"),
                new Permission("acm.cheat.speed.other")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        Player player;
        switch (args.length) {
            case 2:
                if (!sender.hasPermission("acm.cheat.speed.other")) {
                    this.messagePermission(sender);
                    break;
                }
                player = ACMCore.getInstance().getServer().getPlayer(args[2]);
                if (player == null) {
                    ACMCore.getInstance().messager().message(sender, "Player _0_ not found...", ChatColor.RED + args[2]);
                    break;
                }
                int speed = 0;
                try {
                    speed = Integer.parseInt(args[0]);
                } catch (final NumberFormatException e) {
                    ACMCore.getInstance().messager().message(sender, "&cInvalid speed _0_...", args[0]);
                    break;
                }
                if (args[1].equals("fly")) {
                    player.setFlySpeed(speed / 10.0f);
                    ACMCore.getInstance().messager().message(sender, "Setting fly speed to _1_ for _0_...", ChatColor.GREEN + player.getName(), ChatColor.GREEN + "" + speed);
                } else if (args[1].equals("walk")) {
                    player.setWalkSpeed(speed / 10.0f);
                    ACMCore.getInstance().messager().message(sender, "Setting walk speed to _1_ for _0_...", ChatColor.GREEN + player.getName(), ChatColor.GREEN + "" + speed);
                } else {
                    ACMCore.get().messager().message(sender, "&cInvalid speed type _0_...", args[1]);
                    break;
                }
                ACMCore.getInstance().messager().message(player, "Speed changed to _0_...", "" + ChatColor.GREEN + speed);
                break;
            case 1:
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
            case 3:
                for (final OfflinePlayer key : ACMCore.getInstance().getServer().getOfflinePlayers()) {
                    list.add(key.getName());
                }
                for (final Player key : ACMCore.getInstance().getServer().getOnlinePlayers()) {
                    list.add(key.getName());
                }
                break;
            case 2:
                list.add("walk");
                list.add("fly");
                break;
            case 1:
                for (int i = 0; i <= 10; i++) {
                    list.add("" + i);
                }
                break;
            default:
                break;
        }
        return list;
    }
}
