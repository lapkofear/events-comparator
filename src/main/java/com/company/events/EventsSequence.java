package com.company.events;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.stream.Collectors;


@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
class EventsSequence {
    private static final String ID_EVENTS_SEPARATOR = "\t";

    private final String id;
    private final List<Event> events;

    static EventsSequence fromString(String rawString) {
        int idDelimiterPosition = rawString.indexOf(ID_EVENTS_SEPARATOR);

        String id = rawString.substring(0, idDelimiterPosition);
        List<Event> events = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(rawString.substring(idDelimiterPosition + 1, rawString.length() - 1), "()");
        while (tokenizer.hasMoreElements()) {
            events.add(new Event(tokenizer.nextToken()));
        }

        return new EventsSequence(id, events);
    }

    static EventsSequence getOuterJoinOfSequences(EventsSequence current1, EventsSequence current2) {
        if (current1 == null) {
            return current2;
        }
        if (current2 == null) {
            return current1;
        }

        int compare = current1.id.compareTo(current2.id);
        if (compare == 0) {
            if (current1.equals(current2)) {
                return null;
            }
            TreeSet<Event> uniqEvents = new TreeSet<>(Event.COMPARATOR);

            uniqEvents.addAll(current1.getAbsentEventsInSequence(current2));
            uniqEvents.addAll(current2.getAbsentEventsInSequence(current1));
            return new EventsSequence(current1.id, new ArrayList<>(uniqEvents));
        }

        return compare > 0 ? current2 : current1;
    }

    private List<Event> getAbsentEventsInSequence(EventsSequence other) {
        return this.getEvents().stream().filter(event -> !other.getEvents().contains(event)).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.id + ID_EVENTS_SEPARATOR + this.events.stream().map(Event::toString).collect(Collectors.joining());
    }
}
