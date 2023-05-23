package com.bsuir.recreation_facility.app.screens.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.views.auth.RegistrationViewModel
import com.bsuir.recreation_facility.databinding.FragmentRegistrationBinding
import java.text.SimpleDateFormat
import java.util.*


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModels<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        binding.apply {
            dtnSelectedDate.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager

                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        dateOfBirth.text = date
                    }
                }
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }
        }
        binding.apply {
            btSend.setOnClickListener {
                val formatter = SimpleDateFormat("MM-dd-yyyy hh:mm:ss", Locale("Europe/Minsk"))
                val dateOfBirth = formatter.parse(dateOfBirth.text.toString())
                var user = User(
                    id = 0L,
                    name = edName.text.toString(),
                    surname = edSurname.text.toString(),
                    patronymic = edPatronymic.text.toString(),
                    dateOfBirth = dateOfBirth,
                    phone = edPhone.text.toString(),
                    email = edEmail.text.toString(),
                    username = edUsername.text.toString(),
                    password = "",
                    lastLoginDateDisplay = Date(), lastLoginDate = Date(),
                    confirmation = false,
                    isNotLocked = true,
                    isActive = true
                )
                viewModel.registration(user)
            }
        }
        viewModel.register(context, activity, binding)
        return binding.root
    }


}