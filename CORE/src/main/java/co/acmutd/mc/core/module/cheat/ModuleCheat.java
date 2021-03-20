package co.acmutd.mc.core.module.cheat;

import co.acmutd.mc.core.module.Module;
import co.acmutd.mc.core.module.cheat.command.CommandFeed;
import co.acmutd.mc.core.module.cheat.command.CommandFly;
import co.acmutd.mc.core.module.cheat.command.CommandHeal;

public class ModuleCheat extends Module {
    public ModuleCheat() {
        super("cheat", "acm.cheat");
        this.addCommand(new CommandHeal());
        this.addCommand(new CommandFeed());
        this.addCommand(new CommandFly());
        this.addWildcardPermission();
    }
}
