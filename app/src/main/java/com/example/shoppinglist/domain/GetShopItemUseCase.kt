package com.example.shoppinglist.domain

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getItem(id:Int): ShopItem {
        return shopListRepository.getItem(id)
    }
}