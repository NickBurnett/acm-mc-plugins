package co.acmutd.mc.core.module.core.listener;

import co.acmutd.mc.api.event.Listener;
import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.UserData;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ListenerPlayerConnection extends Listener {
    public ListenerPlayerConnection() {}
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onPlayerLogin(final PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) return;
        final UserData data = new UserData(event.getPlayer().getUniqueId());
        data.loadData();
        ACMCore.get().getCache().getUsers().put(data.getUniqueId(), data);
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onPlayerJoin(final PlayerJoinEvent event) {
        ACMCore.get().getCache().getUsers().get(event.getPlayer().getUniqueId()).reloadPermissions();
        event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&a&l+&8&l] &a&l" + event.getPlayer().getName()));
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public final void onPlayerQuit(final PlayerQuitEvent event) {
        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&8&l[&c&l-&8&l] &c&l" + event.getPlayer().getName()));
        ACMCore.get().getCache().getUsers().remove(event.getPlayer().getUniqueId());
        ACMCore.get().getCache().getWizards().remove(event.getPlayer().getUniqueId());
    }
}
