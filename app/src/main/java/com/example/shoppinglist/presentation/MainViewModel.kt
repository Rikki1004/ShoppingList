package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.domain.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppDatabase.getInstance(application)

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