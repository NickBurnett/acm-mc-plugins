package com.acmutd.mc.core.module.core.listener;

import com.acmutd.mc.api.event.Listener;
import com.acmutd.mc.core.ACMCore;
import com.acmutd.mc.core.util.UtilityCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class ListenerPlayerMove  extends Listener {
    public ListenerPlayerMove() {
        super();
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onPlayerMove(final PlayerMoveEvent event) {
        final UtilityCache cache = ACMCore.get().getCache();
        if (cache.getAfk().contains(event.getPlayer().getUniqueId())) {
            cache.getAfk().remove(event.getPlayer().getUniqueId());
            ACMCore.getInstance().messager().message(event.getPlayer(), "You are no longer afk...");
        }
    }
}
