package co.acmutd.mc.api.command;

import java.util.HashMap;
import java.util.Map;

public class Permission {
    private final String node;
    private final String description;
    private final Map<String, Boolean> children;
    public Permission(final String node) {
        this(node, "");
    }
    public Permission(final String node, final String description) {
        this.node = node;
        this.description = description;
        this.children = new HashMap<>();
    }
    public final org.bukkit.permissions.Permission build() {
        return new org.bukkit.permissions.Permission(this.getNode(), this.description, this.children);
    }
    public final String getNode() {
        return this.node;
    }
    public final String getDescription() {
        return this.description;
    }
    public final Map<String, Boolean>  getChildren() {
        return this.children;
    }
    public final Permission addChild(final String key) {
        this.getChildren().put(key, true);
        return this;
    }
}
