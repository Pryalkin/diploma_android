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
import com.bsuir.recreation_facility.app.model.Room
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.*
import com.bsuir.recreation_facility.app.screens.app.Navigator

import com.bsuir.recreation_facility.databinding.FragmentCabinetForViewingPage2Binding
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay

import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*


class CabinetViewModel(
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

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users
    private val listenerUsers: UsersListener = {
        _users.value = it
    }

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees
    private val listenerEmployees: EmployeesListener = {
        _employees.value = it
    }

    private val _friends = MutableLiveData<List<Employee>>()
    val friends: LiveData<List<Employee>> = _friends
    private val listenerFriends: EmployeesListener = {
        _friends.value = it
    }

    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> = _rooms
    private val listenerRooms: RoomsListener = {
        _rooms.value = it
    }

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentCabinetForViewingPage2Binding

    init {
        loadUsers()
        loadEmployees()
    }

    private fun loadEmployees() {
        cabinetRepository.addListenerEmployees(listenerEmployees)
    }

    private fun loadUsers() {
        cabinetRepository.addListenerUsers(listenerUsers)
    }

    fun getEmployee() {
        viewModelScope.launch {
            var res: Response<Employee> = cabinetRepository.getEmployee()
            if (res.isSuccessful){
                listenerEmployee(res.body()!!)
                binding.apply {
                    Glide.with(context)
                        .load(R.drawable.ic_person)
                        .into(imageView)
                    tName.text = "Имя: ${employee.value!!.user!!.name}"
                    tSurname.text = "Фамилия: ${employee.value!!.user!!.surname}"
                    tPatronymic.text = "Отчество: ${employee.value!!.user!!.patronymic}"
                    tDateOfBirth.text = "Дата рождения: ${employee.value!!.user!!.dateOfBirth}"
                    tJobTitle.text = "Должность: ${employee.value!!.jobTitle!!.name}"
                    tRole.text = "Роль: ${employee.value!!.role}"
                    tUsername.text = "Username: ${employee.value!!.user!!.username}"
                    tEmail.text = "Email: ${employee.value!!.user!!.email}"
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentCabinetForViewingPage2Binding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun getRole(): String? {
        return cabinetRepository.getRole()
    }

    fun getAListOfUnregisteredUsers() {
        viewModelScope.launch {
            var res: Response<List<User>> = cabinetRepository.getAListOfUnregisteredUsers()
            if (res.isSuccessful) {
                cabinetRepository.setUsersListener(res.body()!!)
                listenerUsers(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun addUsersListener() {
        cabinetRepository.addListenerUsers(listenerUsers)
    }

    fun removeUsersListener() {
        cabinetRepository.removeListenerUsers(listenerUsers)
    }

    fun addEmployeesListener() {
        cabinetRepository.addListenerEmployees(listenerEmployees)
    }

    fun removeEmployeesListener() {
        cabinetRepository.removeListenerEmployees(listenerEmployees)
    }

    fun registerEmployee(username: String, navigator: Navigator?) {
        viewModelScope.launch {
            var res: Response<List<User>> = cabinetRepository.registerEmployee(username)
            if (res.isSuccessful) {
                cabinetRepository.setUsersListener(res.body()!!)
                listenerUsers(res.body()!!)
                navigator?.toast("Пользователь успешно зарегистрирован!")
                navigator?.goBack()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteUser(username: String, navigator: Navigator?) {
        viewModelScope.launch {
            var res: Response<List<User>> = cabinetRepository.deleteUser(username)
            if (res.isSuccessful) {
                cabinetRepository.setUsersListener(res.body()!!)
                listenerUsers(res.body()!!)
                navigator?.toast("Пользователь успешно удален!")
                navigator?.goBack()
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getAListOfUnregisteredEmployees() {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.getAListOfUnregisteredEmployees()
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun registerAnEmployeeInTheDepartment(username: String, employeename: String, name: String?) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.registerAnEmployeeInTheDepartment(username, employeename, name!!)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun deleteEmployeeFromDepartment(username: String, employeename: String) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.deleteEmployeeFromDepartment(username, employeename)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getGroupname(): String {
        return cabinetRepository.getGroupname()
    }

    fun getUsername(): String? {
        return cabinetRepository.getUsername()
    }

    fun addJobTitle(username: String, employeename: String, jobTitle: String) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.addJobTitle(username, employeename, jobTitle)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun deleteJobTitle(username: String, employeename: String) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.deleteJobTitle(username, employeename)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getEmployeeForUpdate(username: String, navigator: Navigator) {
        viewModelScope.launch {
            delay(1500)
            val emp = employees.value!!.filter {
                it.user.username == username
            }.toList()
            navigator.goBack()
            if (emp.isNotEmpty()) navigator.showEmployee(emp[0])
        }
    }

    fun setPosition(username: String, employeename: String, flag: Boolean) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.setPosition(username, employeename, flag)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun setSumma(summa: Float) {
        cabinetRepository.setSumma(summa)
    }

    fun getSumma(): Float? {
        return cabinetRepository.getSumma()
    }

    fun addStimulation(employeename: String, stimulation: Stimulation) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.addStimulation(employeename, stimulation)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun createStimulation(employeename: String, message: String, navigator: Navigator) {
        val user = User(
            0L, "", "", "",
            Date(), "", "", cabinetRepository.getUsername()!!, "",
            Date(), Date(), false, isNotLocked = false, isActive = false
        )
        val employee = Employee(0L, user, null, null, null, null, null, null)
        val stimulation = Stimulation(0L, employee, message, cabinetRepository.getSumma()!!, Date())
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.addStimulation(employeename, stimulation)
            if (res.isSuccessful) {
                cabinetRepository.setEmployeesListener(res.body()!!)
                listenerEmployees(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getCorrespondence(username: String) {
        viewModelScope.launch {
            var res: Response<List<Room>> = cabinetRepository.getCorrespondence(username)
            if (res.isSuccessful) {
                cabinetRepository.setRoomsListener(res.body()!!)
                listenerRooms(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun removeRoomsListener() {
        cabinetRepository.removeListenerRooms(listenerRooms)
    }

    fun getFriends(username: String) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = cabinetRepository.getFriends(username)
            if (res.isSuccessful) {
                cabinetRepository.setFriendsListener(res.body()!!)
                listenerFriends(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, res.message(), Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun removeFriendsListener() {
        cabinetRepository.removeListenerFriends(listenerFriends)
    }

    fun addListenerFriends() {
        cabinetRepository.addListenerFriends(listenerFriends)
    }

}


