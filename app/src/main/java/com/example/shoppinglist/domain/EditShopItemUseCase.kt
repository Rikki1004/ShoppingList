package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun moveItem(item: ShopItem){
        shopListRepository.editItem(item)
    }
}