package co.acmutd.mc.core.module.permission;

import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.permission.command.CommandRank;
import co.acmutd.mc.core.module.permission.command.CommandRankSet;

public class ModulePermission extends Module {
    public ModulePermission() {
        super("permission", "acm.permission");
        this.addCommand(new CommandRank());
        this.addCommand(new CommandRankSet());
        this.addWildcardPermission();
    }
}
