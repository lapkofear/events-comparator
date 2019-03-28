package com.company.events;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;



public class EventsSequenceTest {


    @Test
    public void parsingTest() {
        EventsSequence eventsSequence =
                EventsSequence.fromString("46685c61-19c4-43a8-a2b8-23822e777e30\t(2 1487624761000 null)(10 1487625431000 null)(10 1487625948000 null)");
        Assert.assertThat(eventsSequence.getId(), Is.is("46685c61-19c4-43a8-a2b8-23822e777e30"));

        Assert.assertThat(eventsSequence.getEvents(), Is.is(List.of("2 1487624761000 null", "10 1487625431000 null", "10 1487625948000 null")));
    }

    @Test
    public void toStringTest() {
        String rawString = "46685c61-19c4-43a8-a2b8-23822e777e30\t(2 1487624761000 null)(10 1487625431000 null)(10 1487625948000 null)";

        Assert.assertThat(EventsSequence.fromString(rawString).toString(), Is.is(rawString));
    }

}