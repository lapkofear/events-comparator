package com.company.comparator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter(AccessLevel.PACKAGE)

/*
    Contains difference in event sequences
    If both element not null, then there is difference only in events. Otherwise one sequence fully absent in the second one.
    Both elements can't be null.
 */

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public class EventsDifferenceEntry {

    private EventsSequence diff1From2;
    private EventsSequence diff2From1;
    //    private AbsentEventsSequence absentSequence;
    //    private List<AbsentEventInSequence> differenceInEvents;
    //
    //    private EventsDifferenceEntry(List<AbsentEventInSequence> differenceInEvents) {
    //        this.differenceInEvents = differenceInEvents;
    //    }

    public static EventsDifferenceEntry getDifferenceInEvents(EventsSequence current1, EventsSequence current2) {
        return new EventsDifferenceEntry(new EventsSequence(current1.getId(), current1.getAbsentEventsInSequence(current2)),
                new EventsSequence(current2.getId(), current2.getAbsentEventsInSequence(current1)));
    }

    public static EventsDifferenceEntry getAbsentSequenceIn1(EventsSequence eventsSequence) {
        return new EventsDifferenceEntry(null, eventsSequence);
    }

    public static EventsDifferenceEntry getAbsentSequenceIn2(EventsSequence eventsSequence) {
        return new EventsDifferenceEntry(eventsSequence, null);
    }
}
