package com.doubleclick.rovleapp.activities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.doubleclick.rovleapp.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

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
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }


}