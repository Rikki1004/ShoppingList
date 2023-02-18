package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        checkIntent()
        startObservers()

        startListeners()
    }

    private fun startListeners() {
        binding.tiName.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorName()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
        binding.tiCount.addTextChangedListener(object : TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorCount()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun checkIntent(){
        if (intent.hasExtra(EXTRA_PAR_MODE) && intent.hasExtra(EXTRA_PAR_ID)){
            val mode = intent.getStringExtra(EXTRA_PAR_MODE)
            val id = intent.getIntExtra(EXTRA_PAR_ID,ShopItem.UNEXPECTED_ID)
            if (mode == MODE_EDIT && id != ShopItem.UNEXPECTED_ID){
                viewModel.getShopItem(id)
                binding.buttonAddShopItem.setOnClickListener {
                    viewModel.editShopItem(
                        binding.tiName.text.toString(),
                        binding.tiCount.text.toString(),
                    )
                }
            }
            else if (mode == MODE_ADD){
                binding.buttonAddShopItem.setOnClickListener {
                    viewModel.addShopItem(
                        binding.tiName.text.toString(),
                        binding.tiCount.text.toString(),
                    )
                }
            }
            else throw java.lang.Exception("err")
        }
        else throw java.lang.Exception("err")
    }

    private fun startObservers() {
        viewModel.nameError.observe(this) {
            if (it)
                binding.tilName.error = getString(R.string.error_fill_field)
            else
                binding.tilName.error = null
        }

        viewModel.countError.observe(this) {
            if (it)
                binding.tilCount.error = getString(R.string.error_fill_field)
            else
                binding.tilCount.error = null
        }

        viewModel.canClose.observe(this){
            finish()
        }

        viewModel.shopItem.observe(this){
            binding.tiName.setText(it.name)
            binding.tiCount.setText(it.count.toString())
        }
    }
    companion object{
        private const val EXTRA_PAR_MODE = "extra_mode"
        private const val EXTRA_PAR_ID = "extra_id"
        private const val MODE_EDIT = "edit"
        private const val MODE_ADD = "add"

        fun getAddIntent(context: Context) : Intent {
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_PAR_MODE, MODE_ADD)
            intent.putExtra(EXTRA_PAR_ID, ShopItem.UNEXPECTED_ID)
            return intent
        }
        fun getEditIntent(context: Context,id:Int) : Intent {
            val intent = Intent(context,ShopItemActivity::class.java)
            intent.putExtra(EXTRA_PAR_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_PAR_ID, id)
            return intent
        }
    }
}