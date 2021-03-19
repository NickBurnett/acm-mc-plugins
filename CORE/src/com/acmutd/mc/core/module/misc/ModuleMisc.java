package com.acmutd.mc.core.module.misc;

import com.acmutd.mc.core.module.Module;
import com.acmutd.mc.core.module.misc.command.CommandNickname;
import com.acmutd.mc.core.module.misc.listener.ListenerPlayerChat;

public class ModuleMisc extends Module {
    public ModuleMisc() {
        super("misc", "acm.misc");
        this.addCommand(new CommandNickname());
        this.addListener(new ListenerPlayerChat());
        this.addWildcardPermission();
    }
}
