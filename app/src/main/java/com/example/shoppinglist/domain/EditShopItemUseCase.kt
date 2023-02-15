package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {
    fun editItem(item: ShopItem){
        shopListRepository.editItem(item)
    }
}