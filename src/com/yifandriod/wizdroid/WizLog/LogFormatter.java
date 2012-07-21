package com.yifandriod.wizdroid.WizLog;

/**
 * Created by IntelliJ IDEA.
 * User: ywang
 * Date: 21/07/12
 * Time: 3:34 PM
 * To change this template use File | Settings | File Templates.
 */

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogFormatter {
    public static final String NL = "\r\n";
    public static final String DEFAULT_DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss.SSS";

    private final DateFormat dateFormat;

    public LogFormatter(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public static LogFormatter newInstance() {
        return new LogFormatter(new SimpleDateFormat(DEFAULT_DATE_FORMAT_NOW));
    }

    public String format(LogLevel level, Object message, Throwable throwable) {
        StringBuilder builder = new StringBuilder();

        String timestamp = dateFormat.format(new Date());
        builder.append(timestamp).append('|');
        builder.append(Thread.currentThread().getName()).append('|');
        builder.append(level.getDisplay()).append('|');
        builder.append("AP").append('|');

        if (message != null) {
            builder.append(message).append('|');
        }

        if (throwable != null) {
            String throwableDisplay = toString(throwable);
            builder.append(throwableDisplay).append('|');
        }

        builder.append(NL);

        return builder.toString();
    }

    private String toString(Throwable throwable) {
        CharArrayWriter output = new CharArrayWriter();
        throwable.printStackTrace(new PrintWriter(output));
        return output.toString();
    }
}
