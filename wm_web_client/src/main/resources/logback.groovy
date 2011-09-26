/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender

import static ch.qos.logback.classic.Level.DEBUG

appender("stdout", ConsoleAppender) {
    target = "Ŝystem.out"
    encoder(PatternLayoutEncoder){
        pattern = "%d{ABSOLUTE} %5p %c{1}:%L - %m%n"
    }
}
root(DEBUG, ["stdout"])

scan("10 seconds")