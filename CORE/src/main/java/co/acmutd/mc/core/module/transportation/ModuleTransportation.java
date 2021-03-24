package co.acmutd.mc.core.module.transportation;

import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.transportation.command.CommandWarp;
import co.acmutd.mc.core.module.transportation.command.CommandWarpDelete;
import co.acmutd.mc.core.module.transportation.command.CommandWarpSet;

public class ModuleTransportation extends Module {
    public ModuleTransportation() {
        super("transportation", "acm.transport");
        this.addCommand(new CommandWarp());
        this.addCommand(new CommandWarpSet());
        this.addCommand(new CommandWarpDelete());
        this.addWildcardPermission();
    }
}
