package co.acmutd.mc.api.util.logger;

public enum AnsiDecor {
    RESET(byCode(0)),
    BOLD(byCode(1)),
    FAINT(byCode(2)),
    ITALIC(byCode(3)),
    UNDERLINE(byCode(4)),
    REVERSED(byCode(7))
    ;
    private final String ansi;
    AnsiDecor(final String ansi) {
        this.ansi = ansi;
    }
    public String getAnsi() {
        return this.ansi;
    }
    public static String byCode(int code) {
        return "\u001b[_C_m".replaceAll("_C_", "" + code);
    }
}