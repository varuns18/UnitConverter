package com.ramphal.unitconverter

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitConverterScreen(
    viewModel: ConversionViewModel,
    modifier: Modifier = Modifier
) {
    var isInputDropdownExpanded by remember { mutableStateOf(false) }
    var isOutputDropdownExpanded by remember { mutableStateOf(false) }
    var showCategorySheet by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        // Category selection button
        TextButton(onClick = { showCategorySheet = true }) {
            Text(
                text = viewModel.selectedCategory,
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
        // Modal Bottom Sheet for category selection
        if (showCategorySheet) {
            ModalBottomSheet(onDismissRequest = { showCategorySheet = false }) {
                Text(
                    text = "Select Conversion Category",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                viewModel.conversionCategories.keys.forEach { category ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.updateCategory(category)
                                showCategorySheet = false
                            }
                            .padding(8.dp)
                    ) {
                        RadioButton(
                            selected = (viewModel.selectedCategory == category),
                            onClick = {
                                viewModel.updateCategory(category)
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
        // Input value text field with unit suffix
        OutlinedTextField(
            value = viewModel.input,
            onValueChange = { viewModel.updateInput(it) },
            label = { Text("Enter value") },
            suffix = { Text(viewModel.inputUnit.name) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Input Unit selection dropdown
        Box {
            TextButton(onClick = { isInputDropdownExpanded = true }, modifier = Modifier.padding(start = 5.dp)) {
                Text(text = viewModel.inputUnit.name)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Input Unit")
            }
            DropdownMenu(
                expanded = isInputDropdownExpanded,
                onDismissRequest = { isInputDropdownExpanded = false }
            ) {
                viewModel.conversionCategories[viewModel.selectedCategory]?.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name) },
                        onClick = {
                            viewModel.updateInputUnit(unit)
                            isInputDropdownExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(28.dp))
        // Converted value output
        OutlinedTextField(
            value = viewModel.output,
            onValueChange = {},
            label = { Text("Converted value") },
            readOnly = true,
            suffix = { Text(viewModel.outputUnit.name) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Output Unit selection dropdown
        Box {
            TextButton(onClick = { isOutputDropdownExpanded = true }, modifier = Modifier.padding(start = 5.dp)) {
                Text(text = viewModel.outputUnit.name)
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Output Unit")
            }
            DropdownMenu(
                expanded = isOutputDropdownExpanded,
                onDismissRequest = { isOutputDropdownExpanded = false }
            ) {
                viewModel.conversionCategories[viewModel.selectedCategory]?.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(text = unit.name) },
                        onClick = {
                            viewModel.updateOutputUnit(unit)
                            isOutputDropdownExpanded = false
                        }
                    )
                }
            }
        }
    }
}
