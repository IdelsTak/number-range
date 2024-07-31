package com.github.idelstak.numrng;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

public class NumbersRangeTest {

    @Test
    @Disabled("Using hyphen delimiter messes up inclusion of negative numbers")
    public void printsRangeWhenGivenHyphenSeparator() {
        // Given
        NumbersRange numbersRange = new NumbersRange("1-4");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("1, 2, 3, 4");
    }

    @Test
    public void printsRangeWhenGivenColonSeparator() {
        // Given
        NumbersRange numbersRange = new NumbersRange("1:4");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("1, 2, 3, 4");
    }

    @Test
    public void printsRangeWhenGivenNegativeNumbers() {
        // Given
        NumbersRange numbersRange = new NumbersRange("-1:2");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("-1, 0, 1, 2");
    }

    @Test
    public void printsRangeRegardlessOfNumbersOrder() {
        // Given
        NumbersRange numbersRange = new NumbersRange("2:-1");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("2, 1, 0, -1");
    }

    @Test
    public void printsRangeGivenStepSize() {
        // Given
        NumbersRange numbersRange = new NumbersRange("6:2:0");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("6, 4, 2, 0");
    }

    @Test
    public void printsCombinedRanges() {
        // Given
        NumbersRange numbersRange = new NumbersRange("30:32,1:4,31");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("30, 31, 32, 1, 2, 3, 4, 31");
    }

    @Test
    public void printsGivenNumber() {
        // Given
        NumbersRange numbersRange = new NumbersRange("30");

        // When
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("30");
    }

    @Test
    public void canPrintUniqueNumbers() {
        // Given
        NumbersRange numbersRange = new NumbersRange("30:32,1:4,31");

        // When
        numbersRange.enableUniqueValues();
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("30, 31, 32, 1, 2, 3, 4");
    }

    @Test
    public void canPrintSortedAscendingNumbers() {
        // Given
        NumbersRange numbersRange = new NumbersRange("32:30");

        // When
        numbersRange.enableAscendingSort();
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("30, 31, 32");
    }

    @Test
    public void canPrintSortedDescendingNumbers() {
        // Given
        NumbersRange numbersRange = new NumbersRange("30:32");

        // When
        numbersRange.enableDescendingSort();
        String range = numbersRange.generateRangeText();

        // Then
        assertThat(range).isEqualTo("32, 31, 30");
    }
}