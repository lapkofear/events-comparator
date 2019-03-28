package com.company.events;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Comparator;

@EqualsAndHashCode
@Getter(AccessLevel.PRIVATE)
class Event {
    static final Comparator<Event> COMPARATOR = Comparator.comparingLong(Event::getTimestamp).thenComparing(Event::getType).thenComparing(Event::getSource);
    private static final char PARAMETERS_SEPARATOR = ' ';
    private static final String START_SYMBOL = "(";
    private static final String END_SYMBOL = ")";
    private long timestamp;
    private String type;
    private String source;

    Event(String rawString) {
        rawString = rawString.replace(START_SYMBOL, "").replace(END_SYMBOL, "");
        int firstDelimiter = rawString.indexOf(PARAMETERS_SEPARATOR);
        int secondDelimiter = rawString.indexOf(PARAMETERS_SEPARATOR, firstDelimiter + 1);
        this.type = rawString.substring(0, firstDelimiter);
        this.timestamp = Long.valueOf(rawString.substring(firstDelimiter + 1, secondDelimiter));
        this.source = rawString.substring(secondDelimiter + 1);
    }

    @Override
    public String toString() {
        return START_SYMBOL + type + PARAMETERS_SEPARATOR + timestamp + PARAMETERS_SEPARATOR + source + END_SYMBOL;
    }
}
