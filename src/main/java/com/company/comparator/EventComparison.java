package com.company.comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Objects;

public class EventComparison {
    private static final Logger LOG = LoggerFactory.getLogger(EventComparison.class.getName());


    public Iterator<EventsDifferenceEntry> findDifferentEntries(Iterator<String> source1, Iterator<String> source2) {
        return new Iterator<>() {
            private EventsDifferenceEntry next = computeNextDiff();

            private EventsSequence prev1;
            private EventsSequence prev2;
            private EventsSequence current1;
            private EventsSequence current2;

            @Override
            public boolean hasNext() {
                return next != null;
            }

            @Override
            public EventsDifferenceEntry next() {
                EventsDifferenceEntry result = this.next;
                this.next = computeNextDiff();
                return result;
            }

            private EventsDifferenceEntry computeNextDiff() {
                if (this.current1 == null && source1.hasNext()) {
                    this.current1 = EventsSequence.parse(source1.next());
                }
                if (this.current2 == null && source2.hasNext()) {
                    this.current2 = EventsSequence.parse(source2.next());
                }

                while (source1.hasNext() || source2.hasNext()) {
                    EventsDifferenceEntry result = null;

                    int cur1toCur2 = this.current1.compareTo(this.current2);
                    if (cur1toCur2 == 0) {
                        if (!Objects.equals(this.current1, this.current2)) {
                            result = EventsDifferenceEntry.getDifferenceInEvents(this.current1, this.current2);
                        }
                        if (source1.hasNext()) {
                            this.prev1 = this.current1;
                            this.current1 = EventsSequence.parse(source1.next());
                        }
                        if (source2.hasNext()) {
                            this.prev2 = this.current2;
                            this.current2 = EventsSequence.parse(source2.next());
                        }
                    }

                    if (cur1toCur2 < 0) {
                        if (this.prev2 != null && this.current1.compareTo(this.prev2) > 0) {
                            result = EventsDifferenceEntry.getAbsentSequenceIn2(current1);
                        }

                        if (source1.hasNext()) {
                            this.prev1 = this.current1;
                            this.current1 = EventsSequence.parse(source1.next());
                        }
                    }

                    if (cur1toCur2 > 0) {
                        if (this.prev1 != null && this.current2.compareTo(this.prev1) > 0) {
                            result = EventsDifferenceEntry.getAbsentSequenceIn2(current2);
                        }
                        if (source2.hasNext()) {
                            this.prev2 = this.current2;
                            this.current2 = EventsSequence.parse(source2.next());
                        }
                    }

                    if (result != null) {
                        return result;
                    }
                }

                return null;
            }
        };
    }
}
