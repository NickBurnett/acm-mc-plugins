package co.acmutd.mc.core.module.core.command;

import co.acmutd.mc.api.command.Command;
import co.acmutd.mc.api.command.Permission;
import co.acmutd.mc.api.data.DataFile;
import co.acmutd.mc.core.ACMCore;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandRefresh extends Command {
    public CommandRefresh() {
        super(
                "refresh",
                "Refreshes the plugin\'s configuration and data.",
                "/refresh",
                new String[] {
                        "reloadconfigs"
                },
                new Permission("acm.core.refresh")
        );
    }

    @Override
    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        switch (args.length) {
            case 0:
                if (!sender.hasPermission("acm.core.refresh")) {
                    this.messagePermission(sender);
                    break;
                }
                ACMCore.get().refresh();
                ACMCore.getInstance().messager().message(sender, "Data refreshed...");
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {
        return null;
    }
}
