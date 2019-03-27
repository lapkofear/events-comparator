package com.company.comparator;


import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class EventComparisonTest {

    @Test
    public void theSameSequencesTest() {
        Iterator<EventsDifferenceEntry> differentEntries = new EventComparison().findDifferentEntries(List.of("id1\t(1 1 1)", "id1\t(2 2 1)").iterator(),
                List.of("id1\t(1 1 1)", "id1\t(2 2 1)").iterator());

        Assert.assertThat(differentEntries.hasNext(), Is.is(false));
    }

    @Test
    public void theSameSequencesTest2() {
        Collection<EventsDifferenceEntry> differentEntries = iterableToCollection(
                new EventComparison().findDifferentEntries(List.of("id1\t(1 1 1)", "id2\t(2 1 1)", "id4\t(2 1 1)").iterator(),
                        List.of("id1\t(1 1 1)", "id3\t(3 1 1), \"id5\\t(2 1 1)\"").iterator()));

        Assert.assertThat(differentEntries, Is.is(List.of(
                EventsDifferenceEntry.getAbsentSequenceIn2(EventsSequence.parse("id1\t(2 1 1)")),
                EventsDifferenceEntry.getAbsentSequenceIn1(EventsSequence.parse("id3\t(3 1 1)")))));
    }

    private static <T> Collection<T> iterableToCollection(Iterator<T> tIterator) {
        Collection<T> collection = new ArrayList<>();
        tIterator.forEachRemaining(collection::add);
        return collection;
    }
}