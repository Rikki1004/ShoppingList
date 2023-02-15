package com.example.shoppinglist.data

class GetShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun getItem(id:Int):ShopItem{
        return shopListRepository.getItem(id)
    }
}