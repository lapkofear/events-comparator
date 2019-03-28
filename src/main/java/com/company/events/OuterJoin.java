package com.company.events;

import java.util.Iterator;

public class OuterJoin {

     public Iterator<String> perform(Iterator<String> source1, Iterator<String> source2) {
        return new Iterator<>() {
            private EventsSequence toCompare1 = getNext(source1);
            private EventsSequence toCompare2 = getNext(source2);
            private EventsSequence next = computeNext();


            @Override
            public boolean hasNext() {
                return this.next != null;
            }

            @Override
            public String next() {
                EventsSequence result = this.next;
                this.next = computeNext();
                return result != null ? result.toString() : null;
            }

            private EventsSequence computeNext() {
                if (toCompare1 == null && toCompare2 == null) {
                    return null;//end of the both sources
                }

                EventsSequence result = EventsSequence.getOuterJoinOfSequences(toCompare1, toCompare2);
                if (result == null) {//they are equal
                    this.toCompare1 = getNext(source1);
                    this.toCompare2 = getNext(source2);
                    return computeNext();
                }

                if (result.equals(toCompare1)) {
                    this.toCompare1 = getNext(source1);
                } else if (result.equals(toCompare2)) {
                    this.toCompare2 = getNext(source2);
                } else {
                    this.toCompare1 = getNext(source1);
                    this.toCompare2 = getNext(source2);
                }
                return result;
            }

            private EventsSequence getNext(Iterator<String> iterator) {
                return iterator.hasNext() ? EventsSequence.fromString(iterator.next()) : null;
            }
        };
    }
}
