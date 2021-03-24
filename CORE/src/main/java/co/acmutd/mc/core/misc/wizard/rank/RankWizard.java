package co.acmutd.mc.core.misc.wizard.rank;

import co.acmutd.mc.api.misc.Wizard;
import co.acmutd.mc.core.ACMCore;
import co.acmutd.mc.core.data.RankData;

import java.util.UUID;

public class RankWizard extends Wizard<RankWizard.RankWizardNode> {
    private final RankData.Rank rank;
    public RankWizard(final UUID user, final boolean focused, final RankData.Rank rank) {
        super(user, focused, RankWizardNode.MENU);
        this.rank = rank;
    }
    private final void prompt() {
        final String[] arr = this.getNode().getPrompt().split("\n");
        for (final String key : arr) {
            ACMCore.getInstance().messager().messageRaw(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), key, false);
        }
    }
    @Override
    public void handle(final String input) {
        switch (this.getNode()) {
            case MENU:
                if (input.equals("1")){
                    this.node = RankWizardNode.PREFIX;
                } else if (input.equals("2")) {
                    this.node = RankWizardNode.PERMISSION;
                } else if (input.equals("3")) {
                    this.node = RankWizardNode.CHILDREN;
                } else if (input.equals("4")) {
                    this.node = RankWizardNode.DELETE;
                } else if (input.equals("5")) {
                    this.node = null;
                    ACMCore.get().getRankData().saveRank(this.rank);
                    this.exitWizard();
                    break;
                }
                this.prompt();
                break;
            case PREFIX:
                if (input.startsWith("$new")) {
                    final String prefix = input.substring(4).trim();
                    if (prefix.isEmpty()) break;
                    this.rank.setPrefix(prefix);
                    ACMCore.getInstance().messager().messageRaw(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "Changed prefix to _0_...", false, prefix);
                } else if (input.equals("$view")) {
                    ACMCore.getInstance().messager().messageRaw(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "Rank prefix: _0_", false, this.rank.getPrefix());
                } else if (input.equals("$quit")) {
                    this.node = RankWizardNode.MENU;
                    this.prompt();
                }
                break;
            case PERMISSION:
                if (input.startsWith("+")) {
                    final String perm = input.substring(1).trim();
                    if (perm.isEmpty()) break;
                    this.rank.getPermissions().add(perm);
                } else if (input.startsWith("-")) {
                    final String perm = input.substring(1).trim();
                    if (perm.isEmpty()) break;
                    this.rank.getPermissions().remove(perm);
                } else if (input.equals("$view")) {
                    this.rank.getPermissions().sort(null);
                    for (final String key : this.rank.getPermissions()) {
                        ACMCore.getInstance().messager().messageRaw(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "&d" + key, false);
                    }
                } else if (input.equals("$quit")) {
                    this.node = RankWizardNode.MENU;
                    this.prompt();
                }
                break;
            case CHILDREN:
                if (input.startsWith("+")) {
                    final String child = input.substring(1).trim();
                    if (child.isEmpty()) break;
                    this.rank.getChildren().add(child);
                } else if (input.startsWith("-")) {
                    final String child = input.substring(1).trim();
                    if (child.isEmpty()) break;
                    this.rank.getChildren().remove(child);
                } else if (input.equals("$view")) {
                    this.rank.getChildren().sort(null);
                    for (final String key : this.rank.getChildren()) {
                        ACMCore.getInstance().messager().messageRaw(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "&d" + key, false);
                    }
                } else if (input.equals("$quit")) {
                    this.node = RankWizardNode.MENU;
                    this.prompt();
                }
                break;
            case DELETE:
                if (input.equals("$confirm")) {
                    ACMCore.get().getRankData().deleteRank(rank);
                    this.node = null;
                    this.exitWizard();
                } else if (input.equals("$quit")) {
                    this.node = RankWizardNode.MENU;
                    this.prompt();
                }
                break;
            default:
                this.exitWizard();
                break;
        }
    }
    @Override
    public void enterWizard() {
        ACMCore.get().getCache().getWizards().put(this.getUniqueId(), this);
        ACMCore.getInstance().messager().messageSpace(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()));
        ACMCore.getInstance().messager().message(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "Entering Rank wizard...");
        this.prompt();
    }
    @Override
    public void exitWizard() {
        ACMCore.get().getCache().getWizards().remove(this.getUniqueId());
        ACMCore.getInstance().messager().message(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()), "Exiting Rank wizard...");
        ACMCore.getInstance().messager().messageSpace(ACMCore.getInstance().getServer().getPlayer(this.getUniqueId()));
    }

    enum RankWizardNode {
        MENU("menu", "&d&lMenu\n&6&l1 &dPrefix\n&6&l2 &dPermissions\n&6&l3 &dChildren\n&6&l4 &dDelete\n&6&l5 &dSave & Exit"),
        PREFIX("prefix", "&d&lPrefix\n&6&l$new <prefix> &dto overwrite the existing prefix\n&6&l$view &dto view the current prefix\n&6&l$quit &dto exit the prefix node"),
        PERMISSION("permission", "&d&lPermissions\n&6&l+<node> &dto enable a permission\n&6&l-<node> &dto disable a permission\n&6&l$view &dto view enabled permissions\n&6&l$quit &dto exit the permission node"),
        CHILDREN("children", "&d&lChildren\n&6&l+<child> &dto add a child\n&6&l-<child> &dto remove a child\n&6&l$view &dto view children\n&6&l$quit &dto exit the children node"),
        DELETE("delete", "&d&lDelete\n&6&l$confirm &dto confirm deletion\n&6&l$quit &dto exit the delete node")
        ;
        private String name;
        private String prompt;
        RankWizardNode(final String name, final String prompt) {
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
