package com.doubleclick.restaurant.activities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import com.doubleclick.domain.model.items.get.Item
import com.doubleclick.domain.model.items.get.Size
import com.doubleclick.domain.ts.OnAddToCart
import com.doubleclick.restaurant.adapter.CategoryAdapter

import com.doubleclick.restaurant.R
import com.doubleclick.restaurant.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment(), OnAddToCart {

    private lateinit var binding: FragmentHomeBinding
//    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var categoryAdapter: CategoryAdapter
    private var items: MutableList<Item> = mutableListOf()

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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        category()
//        items()

    }

//    private fun items() = lifecycleScope.launch {
//        mainViewModel.getItems().collect { response ->
//            if (response.isSuccessful) {
//                response.body()?.data?.let { items ->
//                    this@HomeFragment.items.addAll(items)
//                    binding.rvDishs.layoutAnimation = animation()
//                    binding.rvDishs.adapter = RestaurantDishAdapter(items, this@HomeFragment)
//                }
//            }
//        }
//    }

//    private fun category() = lifecycleScope.launch {
//        mainViewModel.getCategories().collect { response ->
//            if (response.isSuccessful) {
//                response.body()?.data?.let { categories ->
//                    binding.rvCategory.layoutAnimation = animation()
//                    categoryAdapter = CategoryAdapter(categories)
//                    binding.rvCategory.adapter = categoryAdapter
//                    categoryAdapter.onCategoryClick { item ->
//                        binding.rvDishs.layoutAnimation = animation()
//                        binding.rvDishs.adapter = RestaurantDishAdapter(
//                            items.filter { it.category_id == item },
//                            this@HomeFragment
//                        )
//                    }
//                }
//            }
//        }
//    }


    private fun animation(): LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(
            requireActivity(),
            R.anim.layout_animation_fall_down
        )

    override fun addToCart(item: Item, size: Size) {
    }

//    override fun addToCart(item: Item, size: Size) {
//        addCart(item, size)
//    }

//    private fun addCart(item: Item, size: Size) = lifecycleScope.launch {
//        mainViewModel.setCarts(
//            Cart(
//                number = 1,
//                size_id = size.id
//            )
//        ).collect { response ->
//            if (response.isSuccessful) {
//                Toast.makeText(requireActivity(), "Done", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}
