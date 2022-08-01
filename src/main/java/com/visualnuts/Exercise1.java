package com.visualnuts;

import java.security.InvalidParameterException;
import java.util.stream.IntStream;

/**
 * Exercise 1 for Visual Nuts
 *
 * Answers to questions:
 *
 * "How will you keep this code safe from bugs?"
 *
 * A: Mainly using unit tests that have good coverage.
 *
 * "Show how you would guarantee that this code
 *  keeps working when developers start making small feature adjustments. (Maybe we would
 *  want to print the first 500 numbers, ...)."
 *
 *  A: The code is all parametrized and any adjustments should be easy to implement. There are also comments to
 *     facilitate these changes.
 */
public class Exercise1 {

    private int firstDiv;
    private int secondDiv;
    private int bothDiv;
    private String firstMessage;
    private String secondMessage;
    private String bothMessage;

    /**
     * Default case: prints "Visual" if the number is divisible by 3, "Nuts" if it is divisible by 5 or "Visual Nuts"
     * if is divisible by both.
     */
    Exercise1() {
        this(3, 5, "Visual", "Nuts");
    }

    /**
     * General case: prints the first string if the number is divisible by the first divisor,
     *               the second string if it is divisible by the second,
     *               or the concatenation of both messages if it is divisible by both divisors.
     *
     * @param firstDiv first divisor
     * @param secondDiv second divisor
     * @param firstMessage message for the first divisor
     * @param secondMessage  message for the second divisor
     */
    Exercise1(int firstDiv, int secondDiv, String firstMessage, String secondMessage) {
        if (firstDiv <= 0 || secondDiv <= 0) {
            throw new InvalidParameterException("Both divisors must be greater than zero");
        }
        this.firstDiv = firstDiv;
        this.secondDiv = secondDiv;
        this.bothDiv = firstDiv * secondDiv;
        this.firstMessage = firstMessage;
        this.secondMessage = secondMessage;
        this.bothMessage = firstMessage + " " + secondMessage;
    }

    /**
     * Returns an appropriate String as described in the constructor parameters
     *
     * @param number the number to be tested
     * @return a String as described above
     */
    String visualNuts(int number) {
        if (number % bothDiv == 0) {
            return bothMessage;
        }
        if (number % firstDiv == 0) {
            return firstMessage;
        }
        if (number % secondDiv == 0) {
            return secondMessage;
        }
        return String.valueOf(number);
    }

    /**
     * Calls visualNuts on the range [first, last]
     *
     * @param first first number
     * @param last last number
     */
    private void processRange(int first, int last) {
        IntStream.rangeClosed(first, last)
                .forEach((int number) -> System.out.println(visualNuts(number)));
    }

    public static void main(String[] args) {
        Exercise1 exercise1 = new Exercise1();
        exercise1.processRange(1, 100);
    }
}
