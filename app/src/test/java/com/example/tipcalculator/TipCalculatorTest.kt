package com.example.tipcalculator

import org.junit.Test
import org.junit.Assert.*
import java.text.NumberFormat

class TipCalculatorTest {

    @Test
    fun calculate_20_percent_tip_no_roundup(){
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        val actualTip = calculateTip(amount = amount, tipPercent = tipPercent)
        assertEquals("2.00",  "%.2f".format(actualTip))
    }
}