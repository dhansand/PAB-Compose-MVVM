package com.example.myapplication.ui.state

import com.example.myapplication.data.model.InventoryItem

enum class StockSortOption {
    NONE,
    LOWEST_FIRST,  // "stok paling sedikit"
    HIGHEST_FIRST  // "stok paling banyak"
}

data class InventoryUiState(
    val items: List<InventoryItem> = emptyList(),
    val nameInput: String = "",
    val stockInput: Int = 3,
    val nameError: String? = null,
    val sortOption: StockSortOption = StockSortOption.NONE,
    val editingItemId: String? = null
) {
    val isEditing: Boolean
        get() = editingItemId != null

    val displayedItems: List<InventoryItem>
        get() {
            return when (sortOption) {
                StockSortOption.LOWEST_FIRST -> items.sortedBy { it.stock }
                StockSortOption.HIGHEST_FIRST -> items.sortedByDescending { it.stock }
                StockSortOption.NONE -> items
            }
        }

    val isEmpty: Boolean
        get() = displayedItems.isEmpty()
}
