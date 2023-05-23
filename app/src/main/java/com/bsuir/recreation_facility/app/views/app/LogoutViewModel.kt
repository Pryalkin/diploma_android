package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.repositories.LogoutRepository
import com.bsuir.recreation_facility.app.screens.auth.MainActivity
import com.bsuir.recreation_facility.databinding.FragmentLogoutBinding
import kotlinx.coroutines.launch

class LogoutViewModel(
    private val logoutRepository: LogoutRepository = Singletons.logoutRepository
): ViewModel() {

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentLogoutBinding

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentLogoutBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun logout(){
        logoutRepository.logout()
        Toast.makeText(activity, "Вы успешно покинули приложение!", Toast.LENGTH_SHORT).show()
        activity?.startActivity(Intent(activity, MainActivity::class.java))
    }

}