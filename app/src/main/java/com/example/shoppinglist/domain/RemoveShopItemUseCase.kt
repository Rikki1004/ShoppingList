package com.example.shoppinglist.domain

class RemoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun removeItem(item: ShopItem){
        shopListRepository.removeItem(item)
    }
}