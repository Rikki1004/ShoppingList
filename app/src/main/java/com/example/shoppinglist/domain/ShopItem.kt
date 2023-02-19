package com.example.shoppinglist.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shop_items")
data class ShopItem(
    val count:Int,
    val name:String,
    val is_active:Boolean,
    @PrimaryKey(autoGenerate = true)
    val id:Int = UNEXPECTED_ID
){

    companion object{
        const val UNEXPECTED_ID = 0
    }
}
