package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.databinding.FragmentShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemFragment:Fragment() {

    private var _binding: FragmentShopItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ShopItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
        //return inflater.inflate(R.layout.fragment_shop_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding = FragmentShopItemBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        checkInstance()
        startObservers()
        startListeners()

    }


    private fun startListeners() {
        binding.tiName.addTextChangedListener(object : TextWatcher {
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


    private fun checkInstance(){
        val args = requireArguments()
        if (args.containsKey(EXTRA_PAR_MODE) && args.containsKey(EXTRA_PAR_ID)){
            val mode = args.getString(EXTRA_PAR_MODE)
            val id = args.getInt(EXTRA_PAR_ID, ShopItem.UNEXPECTED_ID)
            if (mode == MODE_EDIT && id != ShopItem.UNEXPECTED_ID){
                editMode(id)
            }
            else if (mode == MODE_ADD){
                addMode()
            }
            else throw java.lang.Exception("err")
        }
        else throw java.lang.Exception("err")
    }

    private fun addMode() {
        binding.buttonAddShopItem.setOnClickListener {
            viewModel.addShopItem(
                binding.tiName.text.toString(),
                binding.tiCount.text.toString(),
            )
        }
    }

    private fun editMode(id:Int) {
        viewModel.getShopItem(id)
        binding.buttonAddShopItem.setOnClickListener {
            viewModel.editShopItem(
                binding.tiName.text.toString(),
                binding.tiCount.text.toString(),
            )
        }
    }

    private fun startObservers() {
        viewModel.nameError.observe(viewLifecycleOwner) {
            if (it)
                binding.tilName.error = getString(R.string.error_fill_field)
            else
                binding.tilName.error = null
        }

        viewModel.countError.observe(viewLifecycleOwner) {
            if (it)
                binding.tilCount.error = getString(R.string.error_fill_field)
            else
                binding.tilCount.error = null
        }

        viewModel.canClose.observe(viewLifecycleOwner){
            //requireActivity().finish()
            activity?.onBackPressed()
        }

        viewModel.shopItem.observe(viewLifecycleOwner){
            binding.tiName.setText(it.name)
            binding.tiCount.setText(it.count.toString())
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object{
        const val EXTRA_PAR_MODE = "extra_mode"
        const val EXTRA_PAR_ID = "extra_id"
        const val MODE_EDIT = "edit"
        const val MODE_ADD = "add"

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

        fun getInstanceAddItem() : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_PAR_MODE, MODE_ADD)
                    putInt(EXTRA_PAR_ID, ShopItem.UNEXPECTED_ID)
                }
            }
        }
        fun getInstanceEditItem(id:Int) : ShopItemFragment{
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_PAR_MODE, MODE_EDIT)
                    putInt(EXTRA_PAR_ID, id)
                }
            }
        }
    }
}