package com.acmutd.mc.core.module.permission;

import com.acmutd.mc.core.module.Module;
import com.acmutd.mc.core.module.permission.command.CommandRank;
import com.acmutd.mc.core.module.permission.command.CommandRankSet;

public class ModulePermission extends Module {
    public ModulePermission() {
        super("permission", "acm.permission");
        this.addCommand(new CommandRank());
        this.addCommand(new CommandRankSet());
        this.addWildcardPermission();
    }
}
