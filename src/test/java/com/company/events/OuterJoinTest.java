package com.company.events;


import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class OuterJoinTest {

    @Test
    public void theSameSequencesTest() {
        Iterator<String> differentEntries =
                new OuterJoin().perform(List.of("id1\t(1 1 1)", "id2\t(2 2 1)").iterator(),
                                        List.of("id1\t(1 1 1)", "id2\t(2 2 1)").iterator());

        Assert.assertThat(differentEntries.hasNext(), Is.is(false));
    }

    @Test
    public void theSameSequencesLengthTest() {
        Collection<String> differentEntries = iterableToCollection(
                new OuterJoin().perform(List.of("id1\t(1 1 1)", "id2\t(2 1 1)").iterator(),
                                        List.of("id1\t(1 1 1)", "id3\t(3 1 1)").iterator()));

        Assert.assertThat(differentEntries, Is.is(List.of("id2\t(2 1 1)", "id3\t(3 1 1)")));
    }

    @Test
    public void theDifferentSequencesLengthTest() {
        Collection<String> differentEntries = iterableToCollection(
                new OuterJoin().perform(List.of("id1\t(1 1 1)", "id2\t(2 1 1)").iterator(),
                                        List.of("id1\t(1 1 1)", "id3\t(3 1 1)", "id4\t(4 1 1)").iterator()));

        Assert.assertThat(differentEntries, Is.is(List.of(
                "id2\t(2 1 1)",
                "id3\t(3 1 1)",
                "id4\t(4 1 1)")));
    }

    @Test
    public void combinedDifferencesTest() {
        Collection<String> differentEntries = iterableToCollection(
                new OuterJoin().perform(List.of("id1\t(1 1 1)(2 1 1)", "id2\t(2 1 1)").iterator(),
                                        List.of("id1\t(1 1 1)(3 1 1)").iterator()));

        Assert.assertThat(differentEntries, Is.is(List.of(
                "id1\t(2 1 1)(3 1 1)",
                "id2\t(2 1 1)")));
    }

    private static <T> Collection<T> iterableToCollection(Iterator<T> tIterator) {
        Collection<T> collection = new ArrayList<>();
        tIterator.forEachRemaining(collection::add);
        return collection;
    }
}