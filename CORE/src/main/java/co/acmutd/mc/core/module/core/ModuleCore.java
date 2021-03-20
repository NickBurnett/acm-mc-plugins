package co.acmutd.mc.core.module.core;

import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.core.command.CommandAfk;
import co.acmutd.mc.core.module.core.command.CommandPing;
import co.acmutd.mc.core.module.core.command.CommandRefresh;
import co.acmutd.mc.core.module.core.listener.ListenerPlayerConnection;
import co.acmutd.mc.core.module.core.listener.ListenerPlayerMove;

public class ModuleCore extends Module {
    public ModuleCore() {
        super("core", "acm.core");
        this.addCommand(new CommandAfk());
        this.addCommand(new CommandPing());
        this.addCommand(new CommandRefresh());
        this.addListener(new ListenerPlayerConnection());
        this.addListener(new ListenerPlayerMove());
        this.addWildcardPermission();
    }
}
