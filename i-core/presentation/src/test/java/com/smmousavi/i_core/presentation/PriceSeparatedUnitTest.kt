package com.smmousavi.i_core.presentation

import org.junit.Test

import kotlin.test.assertEquals

class PriceSeparatedUnitTest {
    @Test
    fun priceSeparated_for_less_than_or_equal_to_three_digit_string() {
        val price = 500
        assertEquals(
            expected = "500",
            actual = price.priceSeparated(),
        )
    }

    @Test
    fun priceSeparated_for_four_digit_string() {
        val price = 5000
        assertEquals(
            expected = "5,000",
            actual = price.priceSeparated(),
        )
    }

    @Test
    fun priceSeparated_for_five_digit_string() {
        val price = 50000
        assertEquals(
            expected = "50,000",
            actual = price.priceSeparated(),
        )
    }

    @Test
    fun priceSeparated_six_five_digit_string() {
        val price = 500000
        assertEquals(
            expected = "500,000",
            actual = price.priceSeparated(),
        )
    }

    @Test
    fun priceSeparated_seven_five_digit_string() {
        val price = 5000000
        assertEquals(
            expected = "5,000,000",
            actual = price.priceSeparated(),
        )
    }
}