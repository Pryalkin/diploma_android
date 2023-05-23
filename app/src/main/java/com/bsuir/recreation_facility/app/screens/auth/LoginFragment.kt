package com.bsuir.recreation_facility.app.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.views.auth.LoginViewModel
import com.bsuir.recreation_facility.databinding.FragmentLoginBinding
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.apply {
            btnSend.setOnClickListener {
                val user = User(
                    0L, "", "", "",
                    Date(), "", "", edLogin.text.toString(), edPassword.text.toString(),
                    Date(), Date(), false, isNotLocked = false, isActive = false
                )
                val employee = Employee(0L, user, null, null, null, null, null, null)
                viewModel.login(employee)
            }
            viewModel.register(context, activity, binding)
            return binding.root
        }

    }

}