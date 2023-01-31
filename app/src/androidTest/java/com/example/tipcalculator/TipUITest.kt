package com.example.tipcalculator

import android.util.Log
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.text.NumberFormat

@RunWith(AndroidJUnit4::class)
class TipUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun calculate_20_percent_tip(){
        composeTestRule.setContent{
            TipCalculatorTheme {
                MainLayout()
            }
        }
        composeTestRule.onNodeWithText("Cost of service").performTextClearance()//clear input field
        composeTestRule.onNodeWithText("Cost of service").performTextInput("10")//then give input
        composeTestRule.onNodeWithText("Tip %").performTextClearance()//clear input field
        composeTestRule.onNodeWithText("Tip %").performTextInput("20")//then give input

        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        Log.d("TAG", "calculate_20_percent_tip: $expectedTip")
        composeTestRule.onNodeWithText("Tip amount $expectedTip").assertExists(
            "No node with this text was found."
        )
    }
}