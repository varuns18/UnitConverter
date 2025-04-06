package com.ramphal.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramphal.unitconverter.ui.theme.UnitConverterTheme
import java.text.DecimalFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UnitConverter(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class UnitOption(val name: String, val factor: Double)

val conversionCategories = mapOf(
    "Length/Distance" to listOf(
        UnitOption("Centimeter", 0.01),
        UnitOption("Meter", 1.0),
        UnitOption("Kilometer", 1000.0),
        UnitOption("Inch", 0.0254),
        UnitOption("Foot", 0.3048),
        UnitOption("Yard", 0.9144),
        UnitOption("Mile", 1609.34)
    ),
    "Weight/Mass" to listOf(
        UnitOption("Gram", 0.001),
        UnitOption("Kilogram", 1.0),
        UnitOption("Pound", 0.453592),
        UnitOption("Ounce", 0.0283495),
        UnitOption("Ton", 907.184)
    ),
    "Volume/Capacity" to listOf(
        UnitOption("Milliliter", 0.001),
        UnitOption("Liter", 1.0),
        UnitOption("Cubic Meter", 1000.0),
        UnitOption("Cubic Inch", 0.0163871),
        UnitOption("Cubic Foot", 28.3168),
        UnitOption("Gallon", 3.78541)
    ),
    "Temperature" to listOf(
        UnitOption("Celsius", 1.0),
        UnitOption("Fahrenheit", 1.0),
        UnitOption("Kelvin", 1.0)
    ),
    "Time" to listOf(
        UnitOption("Second", 1.0),
        UnitOption("Minute", 60.0),
        UnitOption("Hour", 3600.0),
        UnitOption("Day", 86400.0),
        UnitOption("Week", 604800.0)
    ),
    "Speed" to listOf(
        UnitOption("Meters per Second", 1.0),
        UnitOption("Kilometers per Hour", 0.277778),
        UnitOption("Miles per Hour", 0.44704),
        UnitOption("Feet per Second", 0.3048)
    ),
    "Area" to listOf(
        UnitOption("Square Meter", 1.0),
        UnitOption("Square Kilometer", 1_000_000.0),
        UnitOption("Square Foot", 0.092903),
        UnitOption("Square Yard", 0.836127),
        UnitOption("Acre", 4046.86),
        UnitOption("Hectare", 10_000.0)
    ),
    "Energy" to listOf(
        UnitOption("Joule", 1.0),
        UnitOption("Kilojoule", 1000.0),
        UnitOption("Calorie", 4.184),
        UnitOption("Kilocalorie", 4184.0),
        UnitOption("Watt-hour", 3600.0),
        UnitOption("Kilowatt-hour", 3_600_000.0)
    ),
    "Fuel Efficiency" to listOf(
        UnitOption("Kilometers per Liter", 1.0),
        UnitOption("Miles per Gallon", 0.425144)
    )
)

fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
    return when (fromUnit to toUnit) {
        "Celsius" to "Fahrenheit" -> (value * 9 / 5) + 32
        "Fahrenheit" to "Celsius" -> (value - 32) * 5 / 9
        "Celsius" to "Kelvin" -> value + 273.15
        "Kelvin" to "Celsius" -> value - 273.15
        "Fahrenheit" to "Kelvin" -> (value - 32) * 5 / 9 + 273.15
        "Kelvin" to "Fahrenheit" -> (value - 273.15) * 9 / 5 + 32
        else -> value // No conversion needed if units are the same
    }
}

fun convertFuelEfficiency(value: Double, fromUnit: String, toUnit: String): Double {
    return when (fromUnit to toUnit) {
        "Kilometers per Liter" to "Miles per Gallon" -> value * 2.35215
        "Miles per Gallon" to "Kilometers per Liter" -> value / 2.35215
        else -> value // No conversion needed if units are the same
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverter(modifier: Modifier = Modifier) {
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(conversionCategories.keys.first()) }
    var inputUnit by remember { mutableStateOf(conversionCategories[selectedCategory]!!.first()) }
    var outputUnit by remember { mutableStateOf(conversionCategories[selectedCategory]!!.first()) }
    var isInputDropdownExpanded by remember { mutableStateOf(false) }
    var isOutputDropdownExpanded by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }

    fun performConversion() {
        val inputValue = input.toDoubleOrNull() ?: return
        val result = when (selectedCategory) {
            "Temperature" -> convertTemperature(inputValue, inputUnit.name, outputUnit.name)
            "Fuel Efficiency" -> convertFuelEfficiency(inputValue, inputUnit.name, outputUnit.name)
            else -> (inputValue * inputUnit.factor) / outputUnit.factor
        }
        output = DecimalFormat("0.##########").format(result)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .absolutePadding(left = 18.dp, top = 24.dp, right = 18.dp, bottom = 18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { showCategorySheet = true }) {
            Text(
                text = selectedCategory,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select Category",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        // Modal Bottom Sheet for category selection with radio buttons
        if (showCategorySheet) {
            ModalBottomSheet(
                onDismissRequest = { showCategorySheet = false }
            ) {
                Text(
                    text = "Select Conversion Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                conversionCategories.keys.forEach { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedCategory = category
                                val units = conversionCategories[category]!!
                                inputUnit = units.first()
                                outputUnit = units.first()
                                performConversion()
                                showCategorySheet = false
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = (selectedCategory == category),
                            onClick = {
                                selectedCategory = category
                                val units = conversionCategories[category]!!
                                inputUnit = units.first()
                                outputUnit = units.first()
                                performConversion()
                                showCategorySheet = false
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = category)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Input Value TextField with Unit Suffix
        OutlinedTextField(
            value = input,
            onValueChange = {
                input = it
                performConversion()
            },
            label = { Text("Enter value") },
            suffix = { Text(inputUnit.name) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Input Unit Selection Dropdown
        Box {
            TextButton(onClick = { isInputDropdownExpanded = true }, Modifier.absolutePadding(left = 5.dp)) {
                Text(text = inputUnit.name)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Input Unit")
            }
            DropdownMenu(
                expanded = isInputDropdownExpanded,
                onDismissRequest = { isInputDropdownExpanded = false }
            ) {
                conversionCategories[selectedCategory]?.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name) },
                        onClick = {
                            inputUnit = unit
                            isInputDropdownExpanded = false
                            performConversion()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Display Converted Output
        OutlinedTextField(
            value = output,
            onValueChange = {},
            label = { Text("Converted value") },
            readOnly = true,
            suffix = { Text(outputUnit.name) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Output Unit Selection Dropdown
        Box {
            TextButton(onClick = { isOutputDropdownExpanded = true }, Modifier.absolutePadding(left = 5.dp)) {
                Text(text = outputUnit.name)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Output Unit")
            }
            DropdownMenu(
                expanded = isOutputDropdownExpanded,
                onDismissRequest = { isOutputDropdownExpanded = false }
            ) {
                conversionCategories[selectedCategory]?.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name) },
                        onClick = {
                            outputUnit = unit
                            isOutputDropdownExpanded = false
                            performConversion()
                        }
                    )
                }
            }
        }

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