package com.example.shoppinglist.data

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun removeItem(item: ShopItem){
        shopListRepository.removeItem(item)
    }
}