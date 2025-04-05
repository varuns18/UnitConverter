package com.ramphal.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramphal.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun UnitConverter(modifier: Modifier) {

    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Centimeter") }
    var outputUnit by remember { mutableStateOf("Centimeter") }
    var iExpend by remember { mutableStateOf(false) }
    var oExpend by remember { mutableStateOf(false) }
    var iconversionFactor by remember { mutableDoubleStateOf(100000.0) }
    var oconversionFactor by remember { mutableDoubleStateOf(100000.0) }

    fun conversionUnit(){
        val inputValueDouble = input.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble/iconversionFactor) * oconversionFactor
        output = result.toString()
    }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Unit Converter", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                conversionUnit()
                            },
            label = { Text(text = "Enter you data")},
            suffix = { Text(text = inputUnit) },
            singleLine = true,
            modifier = Modifier.absolutePadding(left = 10.dp, right = 10.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {

            Box {
                Button(onClick = { iExpend = true }) {
                    Text(text = inputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop Down Arrow")
                }
                DropdownMenu(
                    expanded = iExpend,
                    onDismissRequest = {iExpend = false}
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeter") },
                        onClick = {
                            iExpend = false
                            inputUnit = "Centimeter"
                            iconversionFactor = 100000.0
                            conversionUnit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            iExpend = false
                            inputUnit = "Meter"
                            iconversionFactor = 1000.0
                            conversionUnit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Kilometer") },
                        onClick = {
                            iExpend = false
                            inputUnit = "Kilometer"
                            iconversionFactor = 1.0
                            conversionUnit()
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                Button(onClick = { oExpend = true }) {
                    Text(text = outputUnit)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Drop Down Arrow")
                }
                DropdownMenu(expanded = oExpend, onDismissRequest = {oExpend = false}) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeter") },
                        onClick = {
                            oExpend = false
                            outputUnit = "Centimeter"
                            oconversionFactor = 100000.0
                            conversionUnit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = {
                            oExpend = false
                            outputUnit = "Meter"
                            oconversionFactor = 1000.0
                            conversionUnit()
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Kilometer") },
                        onClick = {
                            oExpend = false
                            outputUnit = "Kilometer"
                            oconversionFactor = 1.0
                            conversionUnit()
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Result: $output $outputUnit", style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        Scaffold(content = { innerPadding ->
            UnitConverter(modifier = Modifier.padding(innerPadding))
        }
        )
    }
}