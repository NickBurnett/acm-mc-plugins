package co.acmutd.mc.api.util.logger;

/**
 * Represents an Ansi Color.
 *
 * RGB Format is \u001b[38;2;R;G;Bm
 * Background is 48 rather than 38
 */
public enum AnsiColor {
    RESET("\u001b[0m", "\u001b[0m"),
    GREEN(rgbForeground(0, 255, 0), rgbBackground(0, 255, 0)),
    LIGHT_GREEN(rgbForeground(145, 255, 145), rgbBackground(145, 255, 145)),
    YELLOW(rgbForeground(255, 255, 0), rgbBackground(255, 255, 0)),
    LIGHT_YELLOW(rgbForeground(255, 255, 100), rgbBackground(255, 255, 100)),
    RED(rgbForeground(255, 0, 0), rgbBackground(255, 0, 0)),
    LIGHT_RED(rgbForeground(255, 60, 60), rgbBackground(255, 60, 60)),
    LIGHTER_RED(rgbForeground(255, 100, 100), rgbBackground(255, 100, 100)),
    PINK(rgbForeground(255, 0, 255), rgbBackground(255, 0, 255)),
    LIGHT_PINK(rgbForeground(255, 150, 255), rgbBackground(255, 150, 255)),
    ORANGE(rgbForeground(255, 130, 0), rgbBackground(255, 130, 0)),
    LIGHT_ORANGE(rgbForeground(255, 170, 85), rgbBackground(255, 170, 85)),
    BLUE(rgbForeground(0, 0, 255), rgbBackground(0, 0, 255)),
    LIGHT_BLUE(rgbForeground(0, 145, 255), rgbBackground(0, 145, 255)),
    GRAY(rgbForeground(90,90,90), rgbForeground(90, 90, 90)),
    LIGHT_GRAY(rgbForeground(160, 160, 160), rgbBackground(160, 160, 160))
    ;

    private final String foreground;
    private final String background;
    AnsiColor(final String foreground, final String background) {
        this.foreground = foreground;
        this.background = background;
    }
    public String getForeground() {
        return this.foreground;
    }
    public String getBackground() {
        return this.background;
    }
    public static String rgbForeground(final int r, final int g, final int b) {
        return "\u001b[38;2;_R_;_G_;_B_m".replaceAll("_R_", "" + r).replaceAll("_G_", "" + g).replaceAll("_B_", "" + b);
    }
    public static String rgbBackground(final int r, final int g, final int b) {
        return "\u001b[48;2;_R_;_G_;_B_m".replaceAll("_R_", "" + r).replaceAll("_G_", "" + g).replaceAll("_B_", "" + b);
    }
}