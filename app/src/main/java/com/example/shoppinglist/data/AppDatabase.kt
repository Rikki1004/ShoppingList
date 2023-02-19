package com.example.shoppinglist.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.domain.ShopItem
import com.example.shoppinglist.domain.ShopListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

@Database(entities = [ShopItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(), ShopListRepository {
    companion object {
        private const val DB_NAME = "maindb"
        private var db: AppDatabase? = null
        private val LOCK = Any()
        fun getInstance(context: Context): AppDatabase {
            synchronized(LOCK){
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()
                db = instance
                return instance
            }
        }
    }
    abstract fun shopItemDao():ShopItemDao



    override fun addItem(item: ShopItem) {
        runBlocking(Dispatchers.IO) {
            shopItemDao().addShopItem(item)
        }
    }

    override fun removeItem(item: ShopItem) {
        runBlocking(Dispatchers.IO) {
            shopItemDao().deleteShopItem(item)
        }
    }

    override fun getItem(id: Int): LiveData<ShopItem> {
        return shopItemDao().getShopItem(id)
    }

    override fun editItem(item: ShopItem) {
        runBlocking(Dispatchers.IO) {
            shopItemDao().editShopItem(item)
        }
    }

    override fun getShopList(): LiveData<List<ShopItem>> {
        return shopItemDao().getShopItems()
    }
}