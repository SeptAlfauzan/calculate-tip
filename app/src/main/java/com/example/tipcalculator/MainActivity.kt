package com.example.tipcalculator

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLayout()
                }
            }
        }
    }
}

@Composable
fun MainLayout() {

    var amountInput: String by remember {
        mutableStateOf("0")
    }

    var tipPercent: String by remember {
        mutableStateOf("15.0")
    }

    val amount = amountInput.toDoubleOrNull() ?: 0.0

    val focusManager = LocalFocusManager.current

    var isRounded: Boolean by remember {
        mutableStateOf(true)
    }

    val percentage = if(isRounded) "%.2f".format(kotlin.math.ceil(calculateTip(amount, tipPercent.toDoubleOrNull() ?: 0.0))) else "%.2f".format(calculateTip(amount, tipPercent.toDoubleOrNull() ?: 0.0))

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Calculate Tip", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 24.sp)
        EditNumField(
            label = "Cost of service",
            value = amountInput,
            onValueChange =  { amountInput = it },
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next)
        )
        EditNumField(
            label = "Tip %",
            value = tipPercent,
            onValueChange =  { tipPercent = it},
            KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        Spacer(Modifier.size(20.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.End),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Round up tip?")
            Switch(
                checked = isRounded,
                onCheckedChange = {
                isRounded = it
            },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = MaterialTheme.colors.primary
                )
            )
        }
        Text("Tip amount $$percentage", modifier = Modifier.align(Alignment.CenterHorizontally), style = TextStyle(
            fontWeight = FontWeight.Bold
        ))
    }
}

@Composable
fun EditNumField(label: String, value: String, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions(), keyboardActions: KeyboardActions = KeyboardActions()){
    TextField(
        value,
        onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    )
}

private fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0
): Double = tipPercent / 100 * amount

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
        MainLayout()
    }
}