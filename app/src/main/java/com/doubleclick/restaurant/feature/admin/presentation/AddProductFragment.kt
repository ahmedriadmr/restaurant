package com.doubleclick.restaurant.presentation.ui.admin.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.doubleclick.domain.model.category.get.Category
import com.doubleclick.domain.model.sizes.Sizes
import com.doubleclick.restaurant.adapter.AddSizesItemAdapter
import com.doubleclick.restaurant.databinding.FragmentAddProductBinding
import com.doubleclick.restaurant.swipetoactionlayout.utils.collapse
import com.doubleclick.restaurant.swipetoactionlayout.utils.expand
import com.doubleclick.restaurant.utils.UploadRequestBody
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "AddProductFragment"

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private lateinit var body: UploadRequestBody
    private var uri: Uri? = null;
    private lateinit var addSizesItemAdapter: AddSizesItemAdapter
    private val list: MutableList<Sizes> = mutableListOf()
    private val categorySelect: MutableList<Category> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        onClick()

    }

    private fun init() {
        addSizesItemAdapter = AddSizesItemAdapter(list)
        binding.rvSizes.adapter = addSizesItemAdapter
    }



    private fun onClick() {
        binding.upload.setOnClickListener {
        }
        binding.selectImage.setOnClickListener {
        }
        binding.selectCatecory.setOnClickListener {
            if (binding.categories.isVisible) {
                collapse(binding.categories)
            } else {
                expand(binding.categories)
            }
        }
        binding.addCategory.setOnClickListener {
        }
        binding.addMore.setOnClickListener {
        }
        addSizesItemAdapter.onItemClick {
            addSizesItemAdapter.notifyItemRemoved(it)
            list.removeAt(it)
        }
    }






}