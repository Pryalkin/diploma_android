package com.bsuir.recreation_facility.app.screens.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.app.views.app.LogoutViewModel
import com.bsuir.recreation_facility.databinding.FragmentLogoutBinding

class LogoutFragment : Fragment() {

    private lateinit var binding: FragmentLogoutBinding
    private val viewModel by viewModels<LogoutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLogoutBinding.inflate(inflater)
        viewModel.register(context, activity, binding)
        viewModel.logout()
        return binding.root
    }

}