package com.doubleclick.rovleapp.activities.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.doubleclick.rovleapp.databinding.FragmentUserProfileBinding
import com.doubleclick.rovleapp.feature.auth.AuthActivity
import com.doubleclick.rovleapp.utils.SessionManger.logout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding


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
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
    }

    private fun onClick() {
        binding.accountFragment.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToAccountFragment())
        }
        binding.myOrdersFragment.setOnClickListener {
            findNavController().navigate(UserProfileFragmentDirections.actionUserProfileFragmentToMyOrdersFragment())
        }
        binding.infoFragment.setOnClickListener {

        }
        binding.logout.setOnClickListener {
            runBlocking {
                logout(requireActivity())
                startActivity(Intent(requireActivity(), AuthActivity::class.java))
                requireActivity().finish()
            }
        }
    }

}