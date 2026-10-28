package com.example.myapplication.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.components.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewmodel.InventoryViewModel

@Composable
fun InventoryScreen(
    modifier: Modifier = Modifier,
    viewModel: InventoryViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFFF7F9F8)
    ) {
        if (isLandscape) {
            // Mode Landscape: 2 Panel berdampingan (Sesuai Modul M11 Bagian 8.4 & 9.4)
            // Panel Kiri (Form) & Panel Kanan (Daftar Stok) masing-masing memiliki scope scroll independen
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Panel Kiri: Form Input (diberi verticalScroll)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AddItemForm(
                        uiState = uiState,
                        onNameChange = viewModel::onNameChange,
                        onStockChange = viewModel::onStockChange,
                        onStockIncrement = viewModel::onStockIncrement,
                        onStockDecrement = viewModel::onStockDecrement,
                        onAddItem = viewModel::addItem
                    )
                }

                // Panel Kanan: Filter & Daftar Stok (menggunakan LazyColumn independen)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StockHeaderAndFilter(
                        uiState = uiState,
                        onSelectSortOption = viewModel::setSortOption
                    )

                    InventoryList(
                        items = uiState.displayedItems,
                        onDeleteItem = viewModel::deleteItem,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        } else {
            // Mode Portrait: Menggunakan Single LazyColumn utama (Sesuai Modul M11 Bagian 9.1 & 10.3)
            // Form, Filter, dan Daftar Stok disatukan dalam LazyColumn untuk scrolling terpadu yang efisien
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    AddItemForm(
                        uiState = uiState,
                        onNameChange = viewModel::onNameChange,
                        onStockChange = viewModel::onStockChange,
                        onStockIncrement = viewModel::onStockIncrement,
                        onStockDecrement = viewModel::onStockDecrement,
                        onAddItem = viewModel::addItem
                    )
                }

                item {
                    StockHeaderAndFilter(
                        uiState = uiState,
                        onSelectSortOption = viewModel::setSortOption
                    )
                }

                if (uiState.displayedItems.isEmpty()) {
                    item {
                        EmptyStateView()
                    }
                } else {
                    items(items = uiState.displayedItems, key = { it.id }) { item ->
                        InventoryItemCard(
                            item = item,
                            onDelete = { viewModel.deleteItem(item.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Portrait Mode", widthDp = 360, heightDp = 740)
@Composable
fun InventoryScreenPortraitPreview() {
    MyApplicationTheme {
        InventoryScreen()
    }
}

@Preview(showBackground = true, name = "Landscape Mode", widthDp = 740, heightDp = 360)
@Composable
fun InventoryScreenLandscapePreview() {
    MyApplicationTheme {
        InventoryScreen()
    }
}
