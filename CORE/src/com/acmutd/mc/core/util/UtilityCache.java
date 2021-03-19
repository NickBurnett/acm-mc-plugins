package com.acmutd.mc.core.util;

import com.acmutd.mc.api.misc.Wizard;
import com.acmutd.mc.api.util.Utility;
import com.acmutd.mc.core.data.UserData;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class UtilityCache extends Utility {
    private final Map<UUID, UserData> users;
    private final Map<UUID, PermissionAttachment> permissions;
    private final Map<UUID, Wizard> wizards;
    private final Set<UUID> afk;
    public UtilityCache() {
        this.users = new HashMap<>();
        this.permissions = new HashMap<>();
        this.wizards = new HashMap<>();
        this.afk = new HashSet<>();
    }
    public final Map<UUID, UserData> getUsers() {
        return this.users;
    }
    public final Map<UUID, PermissionAttachment> getPermissions() {
        return this.permissions;
    }
    public final Map<UUID, Wizard> getWizards() {
        return this.wizards;
    }
    public final Set<UUID> getAfk() {
        return this.afk;
    }
}
