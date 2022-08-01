package com.visualnuts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

class Exercise1Test {

    @Test
    void testNumbers() {
        Exercise1 exercise1 = new Exercise1();
        Assertions.assertEquals(exercise1.visualNuts(1), "1");
        Assertions.assertEquals(exercise1.visualNuts(2), "2");
        Assertions.assertEquals(exercise1.visualNuts(4), "4");
    }

    @Test
    void testVisual() {
        Exercise1 exercise1 = new Exercise1();
        Assertions.assertEquals(exercise1.visualNuts(3), "Visual");
        Assertions.assertEquals(exercise1.visualNuts(6), "Visual");
        Assertions.assertEquals(exercise1.visualNuts(9), "Visual");
        Assertions.assertEquals(exercise1.visualNuts(12), "Visual");
    }

    @Test
    void testNuts() {
        Exercise1 exercise1 = new Exercise1();
        Assertions.assertEquals(exercise1.visualNuts(5), "Nuts");
        Assertions.assertEquals(exercise1.visualNuts(10), "Nuts");
        Assertions.assertEquals(exercise1.visualNuts(20), "Nuts");
        Assertions.assertEquals(exercise1.visualNuts(25), "Nuts");
        Assertions.assertEquals(exercise1.visualNuts(35), "Nuts");
    }

    @Test
    void testVisualNuts() {
        Exercise1 exercise1 = new Exercise1();
        Assertions.assertEquals(exercise1.visualNuts(15), "Visual Nuts");
        Assertions.assertEquals(exercise1.visualNuts(30), "Visual Nuts");
        Assertions.assertEquals(exercise1.visualNuts(45), "Visual Nuts");
    }

    @Test
    void testTwoThree() {
        Exercise1 exercise1 = new Exercise1(2, 3, "Two", "Three");
        Assertions.assertEquals(exercise1.visualNuts(4), "Two");
        Assertions.assertEquals(exercise1.visualNuts(5), "5");
        Assertions.assertEquals(exercise1.visualNuts(6), "Two Three");
        Assertions.assertEquals(exercise1.visualNuts(7), "7");
        Assertions.assertEquals(exercise1.visualNuts(9), "Three");
    }

    @Test
    void testInvalid() {
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            new Exercise1(0, 0, "", "");
        });
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            new Exercise1(0, 3, "", "");
        });
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            new Exercise1(2, 0, "", "");
        });
    }


}