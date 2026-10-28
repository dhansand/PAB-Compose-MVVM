package com.example.myapplication.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.state.InventoryUiState
import com.example.myapplication.ui.state.StockSortOption

@Composable
fun StockHeaderAndFilter(
    uiState: InventoryUiState,
    onSelectSortOption: (StockSortOption) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Daftar Stok",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1C1B1F)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Chip 1: stok paling sedikit
            FilterChip(
                selected = uiState.sortOption == StockSortOption.LOWEST_FIRST,
                onClick = { onSelectSortOption(StockSortOption.LOWEST_FIRST) },
                label = { Text("stok paling sedikit") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE8F5E9),
                    selectedLabelColor = Color(0xFF1E6B52)
                )
            )

            // Chip 2: stok paling banyak
            FilterChip(
                selected = uiState.sortOption == StockSortOption.HIGHEST_FIRST,
                onClick = { onSelectSortOption(StockSortOption.HIGHEST_FIRST) },
                label = { Text("stok paling banyak") },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Color(0xFFE8F5E9),
                    selectedLabelColor = Color(0xFF1E6B52)
                )
            )
        }
    }
}
