package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoppinglist.domain.ShopItem


@Dao
interface ShopItemDao {
    @Query("SELECT * FROM shop_items ORDER BY id DESC")
    fun getShopItems():LiveData<List<ShopItem>>

    @Query("SELECT * FROM shop_items WHERE id == :id LIMIT 1")
    fun getShopItem(id:Int): LiveData<ShopItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(item: ShopItem)

    @Delete
    fun deleteShopItem(item: ShopItem)

    @Update
    fun editShopItem(item: ShopItem)
}