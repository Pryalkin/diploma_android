package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.model.utils.Role
import com.bsuir.recreation_facility.app.repositories.CabinetRepository
import com.bsuir.recreation_facility.app.repositories.EmployeeListener
import com.bsuir.recreation_facility.app.repositories.ErrorListener
import com.bsuir.recreation_facility.databinding.FragmentEmpDetailsBinding
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class EmpDetailsViewModel (
    private val cabinetRepository: CabinetRepository = Singletons.cabinetRepository
): ViewModel() {

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee
    private val listenerEmployee: EmployeeListener = {
        _employee.value = it
    }

    private val _err = MutableLiveData<HttpResponse>()
    val err: LiveData<HttpResponse> = _err
    private val listenerError: ErrorListener = {
        _err.value = it
    }

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentEmpDetailsBinding

    fun registerDetails(context: Context?, activity: FragmentActivity?, binding: FragmentEmpDetailsBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun getEmployee(username: String) {
        viewModelScope.launch {
            var res: Response<Employee> = cabinetRepository.getEmployeeDetails(username)
            if (res.isSuccessful) {
                listenerEmployee(res.body()!!)
                binding.apply {
                    Glide.with(context)
                        .load(R.drawable.ic_person)
                        .into(photeImageView)
                    nameTV.text = "Имя: ${employee.value!!.user.name}"
                    surnameTV.text = "Фамилия: ${employee.value!!.user.surname}"
                    patronymicTV.text = "Отчество: ${employee.value!!.user.patronymic}"
                    dateOfBirthTV.text = "Дата рождения: ${employee.value!!.user.dateOfBirth}"
                    phoneTV.text = "Телефон: ${employee.value!!.user.phone}"
                    usernameTV.text = "Username: ${employee.value!!.user.username}"
                    emailTV.text = "Email: ${employee.value!!.user.email}"
                    jobTitleTV.text = "Должность: ${employee.value!!.jobTitle!!.name}"
                    departmentTV.text = "Отдел: ${employee.value!!.groups!!.name}"
                    when(employee.value!!.role){
                        Role.ROLE_SUPER_ADMIN.name -> positionTV.text = "Позиция: Главный Администратор"
                        Role.ROLE_ADMIN.name -> positionTV.text = "Позиция: Администратор"
                        Role.ROLE_MANAGER.name -> positionTV.text = "Позиция: Менеджер"
                        Role.ROLE_USER.name -> positionTV.text = "Позиция: Сотрудник"
                    }
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}