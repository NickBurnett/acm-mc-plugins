package com.acmutd.mc.core.module.cheat;

import com.acmutd.mc.core.module.Module;
import com.acmutd.mc.core.module.cheat.command.CommandFeed;
import com.acmutd.mc.core.module.cheat.command.CommandFly;
import com.acmutd.mc.core.module.cheat.command.CommandHeal;

public class ModuleCheat extends Module {
    public ModuleCheat() {
        super("cheat", "acm.cheat");
        this.addCommand(new CommandHeal());
        this.addCommand(new CommandFeed());
        this.addCommand(new CommandFly());
        this.addWildcardPermission();
    }
}
