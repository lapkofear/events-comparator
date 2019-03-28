package com.company.events;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EventTest {


    @Test
    public void diffInEventsSequencesTest() {
        EventsSequence sequence1 = EventsSequence.fromString("id1\t(1 1 1)(4 2 null)(202 2 null)(5 3 randomSource)");
        EventsSequence sequence2 = EventsSequence.fromString("id1\t(4 2 null)(4 2 source2)(202 2 null)");

        EventsSequence difference = EventsSequence.getOuterJoinOfSequences(sequence1, sequence2);
        Assert.assertThat(difference.getId(), Is.is("id1"));
        Assert.assertThat(difference.getEvents(),
                          Is.is(List.of(new Event("1 1 1"), new Event("4 2 source2"), new Event("5 3 randomSource"))));
    }

    @Test
    public void toStringTest() {
        Event event = new Event("(5 3 randomSource)");
        Assert.assertThat(event.toString(), Is.is("(5 3 randomSource)"));
    }
}