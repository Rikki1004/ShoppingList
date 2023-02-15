package com.example.shoppinglist.data

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList() : List<ShopItem>{
        return shopListRepository.getShopList()
    }
}