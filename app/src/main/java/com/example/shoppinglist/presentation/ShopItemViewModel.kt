package com.example.shoppinglist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.data.AppDatabase
import com.example.shoppinglist.domain.AddShopItemUseCase
import com.example.shoppinglist.domain.EditShopItemUseCase
import com.example.shoppinglist.domain.GetShopItemUseCase
import com.example.shoppinglist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AppDatabase.getInstance(application)
    private val addShopItemUseCase =  AddShopItemUseCase(repository)
    private val editShopItemUseCase =  EditShopItemUseCase(repository)
    private val getShopItemUseCase =  GetShopItemUseCase(repository)

    private val _nameError = MutableLiveData<Boolean>()
    val nameError :LiveData<Boolean>
        get() = _nameError

    private val _countError = MutableLiveData<Boolean>()
    val countError :LiveData<Boolean>
        get() = _countError

    private lateinit var _shopItem : LiveData<ShopItem>
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
    fun getShopItem(id:Int) : LiveData<ShopItem>{
        _shopItem = getShopItemUseCase.getItem(id)
        return shopItem
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