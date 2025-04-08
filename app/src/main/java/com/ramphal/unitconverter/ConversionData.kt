package com.ramphal.unitconverter

data class UnitOption(val name: String, val factor: Double)

object ConversionRepository {
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
            else -> value // When units are the same
        }
    }

    fun convertFuelEfficiency(value: Double, fromUnit: String, toUnit: String): Double {
        return when (fromUnit to toUnit) {
            "Kilometers per Liter" to "Miles per Gallon" -> value * 2.35215
            "Miles per Gallon" to "Kilometers per Liter" -> value / 2.35215
            else -> value
        }
    }
}
