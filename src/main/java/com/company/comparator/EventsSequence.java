package com.company.comparator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;


@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
class EventsSequence implements Comparable<EventsSequence> {

    private final String id;
    private final List<String> events;

    public static EventsSequence parse(String rawString) {
        int idDelimiterPosition = rawString.indexOf("\t");

        String id = rawString.substring(0, idDelimiterPosition);
        List<String> events = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(rawString.substring(idDelimiterPosition + 1, rawString.length() - 1), "()");
        while (tokenizer.hasMoreElements()) {
            events.add(tokenizer.nextToken());
        }

        return new EventsSequence(id, events);
    }

    public List<String> getAbsentEventsInSequence(EventsSequence other) {
        return this.getEvents().stream().filter(event -> !other.getEvents().contains(event)).collect(Collectors.toList());
    }

    @Override
    public int compareTo(EventsSequence o) {
        return this.id.compareTo(o.id);
    }
}
