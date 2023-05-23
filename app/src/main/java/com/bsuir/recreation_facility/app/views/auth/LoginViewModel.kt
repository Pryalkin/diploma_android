package com.bsuir.recreation_facility.app.views.auth

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.EmployeeRepository
import com.bsuir.recreation_facility.app.repositories.MessageListener
import com.bsuir.recreation_facility.app.screens.app.ApplicationActivity
import com.bsuir.recreation_facility.databinding.FragmentLoginBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val employeeRepository: EmployeeRepository = Singletons.employeeRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val listener: MessageListener = {
        _message.value = it
    }
    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentLoginBinding



    fun login(employee: Employee) {
        viewModelScope.launch {
            var res: Response<Employee> = employeeRepository.login(employee)
            if (res.isSuccessful){
                listener("Вы успешно вошли в систему!")
                binding.edLogin.setText("")
                binding.edPassword.setText("")
                activity?.startActivity(Intent(activity, ApplicationActivity::class.java))
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listener(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message)
            }
            Toast.makeText(context, message.value, Toast.LENGTH_SHORT).show()
        }
    }

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentLoginBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

}