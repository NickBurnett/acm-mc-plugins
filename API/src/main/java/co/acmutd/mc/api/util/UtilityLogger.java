package co.acmutd.mc.api.util;

import co.acmutd.mc.api.ACMPlugin;
import co.acmutd.mc.api.util.logger.AnsiColor;
import co.acmutd.mc.api.util.logger.AnsiDecor;

public class UtilityLogger extends Utility {
    public UtilityLogger() {
        super();
    }
    public void log(final LogLevel level, final String raw, final String... vars) {
        String message = AnsiDecor.BOLD.getAnsi() + AnsiColor.GRAY.getForeground() + "[" + ACMPlugin.getInstance().getDescription().getName() + " | " + level.getColorCode() + level.getId() + AnsiDecor.BOLD.getAnsi() + AnsiColor.GRAY.getForeground() + "] " + level.getColorCode() + raw + AnsiDecor.RESET.getAnsi();
        for (int i = 0; i < vars.length; i++) {
            message = message.replace("_i_".replace("i", "" + i), vars[i] + level.getColorCode());
        }
        System.out.println(message);
    }
    public void info(final String raw, final String... vars) {
        this.log(LogLevel.WHITE, raw, vars);
    }
    public void success(final String raw, final String... vars) {
        this.log(LogLevel.GREEN, raw, vars);
    }
    public void warn(final String raw, final String... vars) {
        this.log(LogLevel.YELLOW, raw, vars);
    }
    public void err(final String raw, final String... vars) {
        this.log(LogLevel.RED, raw, vars);
    }

    public enum LogLevel {
        WHITE(AnsiColor.LIGHT_GRAY.getForeground(), "INFO"),
        GREEN(AnsiColor.GREEN.getForeground(), "SUCCESS"),
        YELLOW(AnsiColor.YELLOW.getForeground(), "WARN"),
        RED(AnsiColor.LIGHT_RED.getForeground(), "ERROR")
        ;
        private final String colorCode;
        private final String id;
        LogLevel(final String colorCode, final String id) {
            this.colorCode = colorCode;
            this.id = id;
        }
        public String getColorCode() {
            return this.colorCode;
        }
        public String getId() {
            return this.id;
        }
    }
}
