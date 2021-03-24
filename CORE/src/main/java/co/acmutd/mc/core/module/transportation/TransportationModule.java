package co.acmutd.mc.core.module.transportation;

import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.transportation.command.CommandWarp;
import co.acmutd.mc.core.module.transportation.command.CommandWarpSet;

public class TransportationModule extends Module {
    public TransportationModule() {
        super("transportation", "acm.transport");
        this.addCommand(new CommandWarp());
        this.addCommand(new CommandWarpSet());
        this.addWildcardPermission();
    }
}
