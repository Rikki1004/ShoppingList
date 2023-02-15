package com.example.shoppinglist.data

class MoveShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun moveItem(item:ShopItem){
        shopListRepository.moveItem(item)
    }
}