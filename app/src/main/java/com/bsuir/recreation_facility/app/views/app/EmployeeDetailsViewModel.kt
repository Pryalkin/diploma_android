package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Department
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.utils.DepartmentConst
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.model.utils.Role
import com.bsuir.recreation_facility.app.repositories.CabinetRepository
import com.bsuir.recreation_facility.app.repositories.EmployeeListener
import com.bsuir.recreation_facility.app.repositories.ErrorListener
import com.bsuir.recreation_facility.databinding.FragmentEmployeeDetailsBinding
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.streams.toList

class EmployeeDetailsViewModel (
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
    lateinit var binding: FragmentEmployeeDetailsBinding

    fun registerDetails(context: Context?, activity: FragmentActivity?, binding: FragmentEmployeeDetailsBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getUserDetails(username: String) {
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
                    val stimulation: List<Stimulation> = employee.value!!.stimulation!!
                    var summa: Double = 0.0
                    if (stimulation.isEmpty()){
                        stimulationTV.text = "Сумма: $summa"
                    } else {
                        summa = stimulation.sumOf { it.sum.toDouble() }
                        stimulationTV.text = "Сумма: $summa"
                    }
                    when(employee.value!!.role){
                        Role.ROLE_SUPER_ADMIN.name -> positionTV.text = "Позиция: Главный Администратор"
                        Role.ROLE_ADMIN.name -> positionTV.text = "Позиция: Администратор"
                        Role.ROLE_MANAGER.name -> positionTV.text = "Позиция: Менеджер"
                        Role.ROLE_USER.name -> positionTV.text = "Позиция: Сотрудник"
                    }
                    when(cabinetRepository.getRole()){
                        Role.ROLE_SUPER_ADMIN.name -> {
                            utilityMethod(Role.ROLE_SUPER_ADMIN.name)
                        }
                        Role.ROLE_ADMIN.name -> {
                            utilityMethod(Role.ROLE_ADMIN.name)
                        }
                        Role.ROLE_MANAGER.name -> {
                            utilityMethod(Role.ROLE_MANAGER.name)
                        }
                    }
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun utilityMethod(role: String) {
        binding.apply {
            if (role == Role.ROLE_SUPER_ADMIN.name || role == Role.ROLE_ADMIN.name){
                btDepartmentTake.visibility = View.INVISIBLE
                if (employee.value!!.groups!!.name == DepartmentConst.FREE){
                    btDepartment.visibility = View.VISIBLE
                    btDepartmentDelete.visibility = View.INVISIBLE
                } else {
                    btDepartment.visibility = View.INVISIBLE
                    btDepartmentDelete.visibility = View.VISIBLE
                }
            } else if (role == Role.ROLE_MANAGER.name){
                btDepartment.visibility = View.INVISIBLE
                if (employee.value!!.groups!!.name == DepartmentConst.FREE){
                    btDepartmentTake.visibility = View.VISIBLE
                    btDepartmentDelete.visibility = View.INVISIBLE
                } else {
                    btDepartmentTake.visibility = View.INVISIBLE
                    btDepartmentDelete.visibility = View.VISIBLE
                }
            }
            if (employee.value!!.jobTitle!!.name == DepartmentConst.FREE){
                btJobTitleDelete.visibility = View.INVISIBLE
                btJobTitle.visibility = View.VISIBLE
            } else {
                btJobTitleDelete.visibility = View.VISIBLE
                btJobTitle.visibility = View.INVISIBLE
            }
            if (employee.value!!.role == role){
                btPositionTrue.visibility = View.GONE
            } else btPositionTrue.visibility = View.VISIBLE
            if (employee.value!!.role == Role.ROLE_USER.name){
                btPositionFalse.visibility = View.GONE
            } else btPositionFalse.visibility = View.VISIBLE
        }
    }

}