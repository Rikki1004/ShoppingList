package com.example.shoppinglist.domain

data class ShopItem(
    val count:Int,
    val name:String,
    val is_active:Boolean,
    var id:Int = UNEXPECTED_ID
){
    companion object{
        const val UNEXPECTED_ID = -1
    }
}
