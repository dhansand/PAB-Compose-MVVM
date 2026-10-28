package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.state.InventoryUiState

val PrimaryTeal = Color(0xFF1E6B52)

@Composable
fun AddItemForm(
    uiState: InventoryUiState,
    onNameChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onStockIncrement: () -> Unit,
    onStockDecrement: () -> Unit,
    onAddItem: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = if (uiState.isEditing) "Edit Barang" else "Tambah Barang",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1C1B1F)
            )

            // Input Nama Barang
            Column {
                Text(
                    text = "Nama barang",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value = uiState.nameInput,
                    onValueChange = onNameChange,
                    placeholder = { Text("Contoh: Beras 5 kg", color = Color.Gray) },
                    isError = uiState.nameError != null,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                )
                if (uiState.nameError != null) {
                    Text(
                        text = uiState.nameError,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }

            // Input Stok dengan Stepper Ringkas (Kotak input tidak full ke layar, sama persis ukurannya dengan tombol - dan +)
            Column {
                Text(
                    text = "Stok (jumlah)",
                    fontSize = 13.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onStockDecrement,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Remove, contentDescription = "Kurang Stok")
                    }

                    OutlinedTextField(
                        value = if (uiState.stockInput > 0) uiState.stockInput.toString() else "",
                        onValueChange = onStockChange,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        modifier = Modifier
                            .width(68.dp)
                            .height(52.dp),
                        shape = RoundedCornerShape(8.dp)
                    )

                    OutlinedButton(
                        onClick = onStockIncrement,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(52.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Tambah Stok")
                    }
                }
            }

            // Tombol Tambah di Sebelah Kanan (Alignment.End)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onAddItem,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryTeal),
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.defaultMinSize(minWidth = 110.dp)
                ) {
                    Text(
                        text = if (uiState.isEditing) "Perbarui" else "Tambah",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
