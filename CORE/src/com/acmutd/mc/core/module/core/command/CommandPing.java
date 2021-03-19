package com.acmutd.mc.core.module.core.command;

import com.acmutd.mc.api.ACMPlugin;
import com.acmutd.mc.api.command.Command;
import com.acmutd.mc.api.command.Permission;
import com.acmutd.mc.core.ACMCore;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandPing extends Command {
    public CommandPing() {
        super(
                "ping",
                "Status-check the plugin.",
                "/ping",
                new String[] {
                        "status"
                },
                new Permission("acm.core.ping")
        );
    }
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        switch (args.length) {
            case 0:
                if (!sender.hasPermission("acm.core.ping")) {
                    this.messagePermission(sender);
                    break;
                }
                ACMPlugin.getInstance().messager().message(sender, "Pong...");
                break;
            default:
                this.messageUsage(sender);
                break;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
