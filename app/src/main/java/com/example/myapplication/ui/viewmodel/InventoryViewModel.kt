package com.example.myapplication.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.model.InventoryItem
import com.example.myapplication.ui.state.InventoryUiState
import com.example.myapplication.ui.state.StockSortOption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InventoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                items = listOf(
                    InventoryItem(name = "Beras 5 kg", stock = 3),
                    InventoryItem(name = "Minyak Goreng 1 L", stock = 4),
                    InventoryItem(name = "Telur 1 kg", stock = 5),
                    InventoryItem(name = "Gula Pasir 1 kg", stock = 12),
                )
            )
        }
    }

    fun onNameChange(newName: String) {
        _uiState.update { it.copy(nameInput = newName, nameError = null) }
    }

    fun onStockChange(newStockStr: String) {
        val stockVal = newStockStr.toIntOrNull() ?: 0
        _uiState.update { it.copy(stockInput = stockVal.coerceAtLeast(0)) }
    }

    fun onStockIncrement() {
        _uiState.update { it.copy(stockInput = it.stockInput + 1) }
    }

    fun onStockDecrement() {
        _uiState.update { it.copy(stockInput = (it.stockInput - 1).coerceAtLeast(1)) }
    }

    fun addItem() {
        val currentState = _uiState.value
        if (currentState.nameInput.isBlank()) {
            _uiState.update { it.copy(nameError = "Nama barang wajib diisi") }
            return
        }

        val newItem = InventoryItem(
            name = currentState.nameInput.trim(),
            stock = currentState.stockInput.coerceAtLeast(1)
        )

        _uiState.update {
            it.copy(
                items = it.items + newItem,
                nameInput = "",
                stockInput = 1,
                nameError = null
            )
        }
    }

    fun deleteItem(id: String) {
        _uiState.update { state ->
            state.copy(items = state.items.filterNot { it.id == id })
        }
    }

    fun setSortOption(option: StockSortOption) {
        _uiState.update {
            val newOption = if (it.sortOption == option) StockSortOption.NONE else option
            it.copy(sortOption = newOption)
        }
    }
}
