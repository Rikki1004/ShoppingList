package com.example.shoppinglist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.*

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl


    private val removeShopItemUseCase = RemoveShopItemUseCase(repository)
    private val getShopListUseCase = GetShopListUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun removeShopItem(item:ShopItem){
        removeShopItemUseCase.removeItem(item)
    }
    fun changeShopItemState(item:ShopItem){
        val newItem = item.copy(is_active = !item.is_active)
        editShopItemUseCase.editItem(newItem)
    }

}