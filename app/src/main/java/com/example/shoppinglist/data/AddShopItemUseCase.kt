package com.example.shoppinglist.data

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun addItem(item: ShopItem){
        shopListRepository.addItem(item)
    }
}