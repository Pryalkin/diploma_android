package com.bsuir.recreation_facility.app.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.views.LoginViewModel
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
                    Date(), Date(), false, isNotLocked = false, isActive = false,
                )
                val employee = Employee(0L, user, "", "", emptyArray(), 0.0)
                viewModel.login(employee)
                Toast.makeText(context, viewModel.message.value, Toast.LENGTH_SHORT).show()
//                startActivity(Intent(activity, ApplicationActivity::class.java))
            }
            return binding.root
        }

    }
}