package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter :ShopListAdapter
    private lateinit var recycleView :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdapter()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.shopList.observe(this){
            println(it)
            adapter.submitList(it)
            adapter
        }

        val buttonAdd = findViewById<FloatingActionButton>(R.id.addButton)
        buttonAdd.setOnClickListener {
            val intent = ShopItemActivity.getAddIntent(this)
            startActivity(intent)
        }

    }
    fun initAdapter(){
        adapter = ShopListAdapter()
        recycleView = findViewById(R.id.rv_shop_List)
        recycleView.adapter = adapter

        adapter.onShopItemLongClickListener = {
            viewModel.changeShopItemState(it)
            //viewModel.removeShopItem(it)
        }
        adapter.onShopItemClickListener = {
            val intent = ShopItemActivity.getEditIntent(this,it.id)
            startActivity(intent)
        }

        val callback = object : SimpleCallback(0, LEFT or RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.removeShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycleView)

    }
}