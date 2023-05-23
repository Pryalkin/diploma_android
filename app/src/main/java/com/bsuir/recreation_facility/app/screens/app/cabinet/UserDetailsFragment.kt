package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.app.views.app.UserDetailsViewModel
import com.bsuir.recreation_facility.databinding.FragmentUserDetailsBinding

class UserDetailsFragment(
) : Fragment() {

    lateinit var binding: FragmentUserDetailsBinding
    private val viewModel by viewModels<UserDetailsViewModel>()
    private val viewModel2 by viewModels<CabinetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater)
        viewModel.registerDetails(context, activity, binding)
        viewModel.getUserDetails(requireArguments().getString(ARG_USER_ID)!!)
        binding.btRegister.setOnClickListener {
            viewModel2.registerEmployee(requireArguments().getString(ARG_USER_ID)!!, navigator())
        }
        binding.btDelete.setOnClickListener {
            viewModel2.deleteUser(requireArguments().getString(ARG_USER_ID)!!, navigator())
        }
        return binding.root
    }

    companion object {
        private const val ARG_USER_ID = "ARG_USER_ID"
        fun newInstance(user: User): UserDetailsFragment{
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf ( ARG_USER_ID to user.username )
            return fragment
        }
    }

}