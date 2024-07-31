package com.github.idelstak.numrng;

import java.util.*;
import java.util.stream.*;

import static java.util.stream.Collectors.*;

public class NumbersRange {

    private final String input;
    private boolean uniqueValues;
    private Boolean isSortedAscending;

    public NumbersRange(String input) {
        this.input = input;
    }

    String generateRangeText() {
        String[] ranges = input.split(",");
        Stream<Integer> rangeStream = Arrays.stream(ranges).flatMap(NumbersRange::parseRange);
        if (uniqueValues) {
            rangeStream = rangeStream.distinct();
        }
        if (isSortedAscending != null) {
            rangeStream = isSortedAscending
                          ? rangeStream.sorted(Comparator.naturalOrder())
                          : rangeStream.sorted(Comparator.reverseOrder());
        }

        return rangeStream.map(Object::toString).collect(joining(", "));
    }

    private static Stream<Integer> parseRange(String rangeText) {
        String[] rangeParts = rangeText.split(":");
        int[] numbers = Arrays.stream(rangeParts).mapToInt(Integer::parseInt).toArray();
        int start = numbers[0];
        int originalStart = start;
        int end = numbers[numbers.length - 1];
        boolean isDescending = start > end;
        int stepTemp = (numbers.length == 3) ? numbers[1] : 1;

        if (isDescending) {
            start = end;
            end = originalStart;
            stepTemp = -stepTemp;
        }

        int maxSize = Math.abs((end - start) / stepTemp) + 1;
        int step = stepTemp;
        return Stream.iterate(originalStart, value -> value + step).limit(maxSize);
    }

    void enableAscendingSort() {
        isSortedAscending = true;
    }

    void enableUniqueValues() {
        uniqueValues = true;
    }

    void enableDescendingSort() {
        isSortedAscending = false;
    }

}