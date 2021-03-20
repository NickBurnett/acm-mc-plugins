package co.acmutd.mc.api.util.logger;

public enum LogLevel {
    INFO(AnsiColor.LIGHT_GRAY, "INFO"),
    WARN(AnsiColor.LIGHT_YELLOW, "WARN"),
    ERROR(AnsiColor.LIGHTER_RED, "ERROR")
    ;
    private final AnsiColor logColor;
    private final String logName;
    LogLevel(final AnsiColor logColor, final String logName) {
        this.logColor = logColor;
        this.logName = logName;
    }
    public AnsiColor getLogColor() {
        return this.logColor;
    }
    public String getLogName() {
        return this.logName;
    }
    public String getLogString() {
        return AnsiDecor.BOLD.getAnsi() + AnsiColor.GRAY.getForeground() + "[" + this.logColor.getForeground() + this.logName + AnsiColor.GRAY.getForeground() + "]" + AnsiColor.RESET.getForeground() + " " + AnsiColor.LIGHT_GRAY.getForeground();
    }
}