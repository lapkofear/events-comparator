package com.company.comparator;


import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class EventsDifferenceEntryTest {


    @Test
    public void diffInEventsSequencesTest() {
        EventsSequence sequence1 = EventsSequence.parse("id1\t(1 1 1)(4 2 null)(202 2 null)(5 3 randomSource)");
        EventsSequence sequence2 = EventsSequence.parse("id1\t(4 2 null)(4 2 source2)(202 2 null)");

        EventsDifferenceEntry difference = EventsDifferenceEntry.getDifferenceInEvents(sequence1, sequence2);

        Assert.assertThat(difference.getDiff1From2(), Is.is(new EventsSequence(sequence1.getId(), List.of("1 1 1", "5 3 randomSource"))));
        Assert.assertThat(difference.getDiff2From1(), Is.is(new EventsSequence(sequence2.getId(), List.of("4 2 source2"))));
    }

}