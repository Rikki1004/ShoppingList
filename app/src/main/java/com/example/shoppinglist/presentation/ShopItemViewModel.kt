package com.example.shoppinglist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppinglist.data.ShopListRepositoryImpl
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem
import java.util.InputMismatchException

class ShopItemViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl
    private val addShopItemUseCase =  AddShopItemUseCase(repository)
    private val editShopItemUseCase =  EditShopItemUseCase(repository)
    private val getShopItemUseCase =  GetShopItemUseCase(repository)

    private val _nameError = MutableLiveData<Boolean>()
    val nameError :LiveData<Boolean>
        get() = _nameError

    private val _countError = MutableLiveData<Boolean>()
    val countError :LiveData<Boolean>
        get() = _countError

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem :LiveData<ShopItem>
        get() = _shopItem

    private val _canClose = MutableLiveData<Unit>()
    val canClose :LiveData<Unit>
        get() = _canClose

    fun addShopItem(inputName:String?,inputCount:String?){
        val name = nameValidator(inputName)
        val count = countValidator(inputCount)
        if (validator(name,count)){
            val item = ShopItem(count,name,true)
            addShopItemUseCase.addItem(item)
            needClose()
        }

    }
    fun editShopItem(inputName:String?,inputCount:String?){
        val name = nameValidator(inputName)
        val count = countValidator(inputCount)
        if (validator(name,count)){
            _shopItem.value?.let {
                val item = it.copy(count = count,name = name)
                editShopItemUseCase.editItem(item)
                needClose()
            }
        }
    }
    fun getShopItem(id:Int){
        val item = getShopItemUseCase.getItem(id)
        _shopItem.value = item

    }


    private fun nameValidator(name:String?):String{
        return name?.trim()?:""
    }
    private fun countValidator(count:String?):Int{
        return try {
            count?.trim()?.toInt()?:0
        } catch (e:java.lang.Exception){
            0
        }
    }
    private fun validator(name: String,count: Int):Boolean{
        var result = true
        if (name.isBlank()){
            _nameError.value = true
            result = false
        }
        if (count <= 0){
            _countError.value = true
            result = false
        }
        return result
    }

    fun resetErrorName(){
        _nameError.value = false
    }
    fun resetErrorCount(){
        _countError.value = false
    }

    private fun needClose(){
        _canClose.value = Unit
    }

}