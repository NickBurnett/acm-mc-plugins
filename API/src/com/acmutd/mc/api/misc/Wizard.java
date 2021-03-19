package com.acmutd.mc.api.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Wizard<T extends Enum<T>> {
    private final UUID user;
    private final boolean focused;
    protected T node;
    public Wizard(final UUID user, final boolean focused, final T start) {
        this.user = user;
        this.focused = focused;
        this.node = start;
    }
    public final UUID getUniqueId() {
        return this.user;
    }
    public final boolean isFocused() {
        return this.focused;
    }
    public final T getNode() {
        return this.node;
    }

    public abstract void handle(final String input);
    public abstract void enterWizard();
    public abstract void exitWizard();
    public abstract static class WizardNode {
        protected final String name;
        protected final String prompt;

        public WizardNode(final String name, final String prompt) {
            this.name = name;
            this.prompt = prompt;
        }
        public final String getName() {
            return this.name;
        }
        public final String getPrompt() {
            return this.prompt;
        }
    }
}
