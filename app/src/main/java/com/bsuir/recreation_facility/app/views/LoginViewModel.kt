package com.bsuir.recreation_facility.app.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.EmployeeListener
import com.bsuir.recreation_facility.app.repositories.EmployeeRepository
import com.bsuir.recreation_facility.app.repositories.UserRepository
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(
    private val employeeRepository: EmployeeRepository = Singletons.employeeRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val listener: EmployeeListener = {
        _message.value = it
    }

    fun login(employee: Employee) {
        viewModelScope.launch {
            var res: Response<Employee> = employeeRepository.login(employee)
            if (res.isSuccessful){
                listener("Вы успешно вошли в систему!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listener(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message)
            }
        }
    }
}