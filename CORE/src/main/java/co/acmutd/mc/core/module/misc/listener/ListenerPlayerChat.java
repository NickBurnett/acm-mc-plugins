package co.acmutd.mc.core.module.misc.listener;

import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.UserData;
import co.acmutd.mc.core.util.UtilityCache;
import co.acmutd.mc.api.event.Listener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Iterator;

public class ListenerPlayerChat extends Listener {
    public ListenerPlayerChat() {}
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onPlayerChat(final AsyncPlayerChatEvent event) {
        final UtilityCache cache = ACMCore.get().getCache();
        if (cache.getWizards().containsKey(event.getPlayer().getUniqueId())) {
            cache.getWizards().get(event.getPlayer().getUniqueId()).handle(event.getMessage());
            System.out.println(event.getMessage());
            event.setCancelled(true);
        }
        final Iterator<Player> itr = event.getRecipients().iterator();
        while (itr.hasNext()) {
            final Player p = itr.next();
            if (!cache.getWizards().containsKey(p.getUniqueId())) continue;
            if (!cache.getWizards().get(p.getUniqueId()).isFocused()) continue;
            itr.remove();
        }
        final UserData data = ACMCore.get().getCache().getUsers().get(event.getPlayer().getUniqueId());
        final String displayName = (data.getNickname().isEmpty()) ? event.getPlayer().getName() : data.getNickname();
        event.setFormat(ChatColor.translateAlternateColorCodes('&', data.getRank().getPrefix() + " &7" + displayName + "&8&l: &7" + event.getMessage()));
    }
}
