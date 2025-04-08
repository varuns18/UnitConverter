package com.ramphal.unitconverter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class ConversionViewModel : ViewModel() {
    // Access conversion data from the repository
    val conversionCategories = ConversionRepository.conversionCategories

    // UI state variables
    var input by mutableStateOf("")
        private set
    var output by mutableStateOf("")
        private set
    var selectedCategory by mutableStateOf(conversionCategories.keys.first())
        private set
    var inputUnit by mutableStateOf(conversionCategories[selectedCategory]!!.first())
        private set
    var outputUnit by mutableStateOf(conversionCategories[selectedCategory]!!.first())
        private set

    // Conversion logic based on the selected category
    fun performConversion() {
        val inputValue = input.toDoubleOrNull() ?: return
        output = when (selectedCategory) {
            "Temperature" -> {
                DecimalFormat("0.##########").format(
                    ConversionRepository.convertTemperature(inputValue, inputUnit.name, outputUnit.name)
                )
            }
            "Fuel Efficiency" -> {
                DecimalFormat("0.##########").format(
                    ConversionRepository.convertFuelEfficiency(inputValue, inputUnit.name, outputUnit.name)
                )
            }
            else -> {
                DecimalFormat("0.##########").format((inputValue * inputUnit.factor) / outputUnit.factor)
            }
        }
    }

    // Update the category and reset units accordingly
    fun updateCategory(category: String) {
        selectedCategory = category
        conversionCategories[category]?.let { units ->
            inputUnit = units.first()
            outputUnit = units.first()
        }
        performConversion()
    }

    // Update input value and perform conversion
    fun updateInput(newInput: String) {
        input = newInput
        performConversion()
    }

    // Update input unit and perform conversion
    fun updateInputUnit(unit: UnitOption) {
        inputUnit = unit
        performConversion()
    }

    // Update output unit and perform conversion
    fun updateOutputUnit(unit: UnitOption) {
        outputUnit = unit
        performConversion()
    }
}
