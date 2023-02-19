package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null)
            checkIntent()
    }


    private fun checkIntent(){
        if (intent.hasExtra(ShopItemFragment.EXTRA_PAR_MODE) && intent.hasExtra(
                ShopItemFragment.EXTRA_PAR_ID
            )){
            val mode = intent.getStringExtra(ShopItemFragment.EXTRA_PAR_MODE)
            val id = intent.getIntExtra(ShopItemFragment.EXTRA_PAR_ID, ShopItem.UNEXPECTED_ID)
            if (mode == ShopItemFragment.MODE_EDIT && id != ShopItem.UNEXPECTED_ID){
                editMode(id)
            }
            else if (mode == ShopItemFragment.MODE_ADD){
                addMode()
            }
            else throw java.lang.Exception("err")
        }
        else throw java.lang.Exception("err")
    }

    private fun addMode() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container,ShopItemFragment.getInstanceAddItem())
            .commit()
    }

    private fun editMode(id:Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container,ShopItemFragment.getInstanceEditItem(id))
            .commit()

    }

}