package com.yifandroid.wizdroid.WizLog;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 3:22 PM
 * To change this template use File | Settings | File Templates.
 */
public enum LogLevel {
    DEBUG(1, "D"),
    INFO(2, "I"),
    WARNING(3, "W"),
    ERROR(4, "E"),
    FATAL(5, "F");

    private final int level;
    private final String display;


    private LogLevel(int logLevel, String display) {
        this.level = logLevel;
        this.display = display;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplay() {
        return display;
    }
}
