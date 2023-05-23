package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.view.Menu
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.DepartmentName
import com.bsuir.recreation_facility.app.model.Emotion
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.utils.Emo
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.*
import com.bsuir.recreation_facility.app.screens.app.Navigator
import com.bsuir.recreation_facility.databinding.FragmentDepartmentForViewingPage2Binding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class DepartmentViewModel(
    private val departmentRepository: DepartmentRepository = Singletons.departmentRepository
): ViewModel() {

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentDepartmentForViewingPage2Binding

    private val _err = MutableLiveData<HttpResponse>()
    val err: LiveData<HttpResponse> = _err
    private val listenerError: ErrorList = {
        _err.value = it
    }

    private val _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements
    private val listenerAnnouncements: AnnouncementsListener = {
        _announcements.value = it
    }

    private val _employees = MutableLiveData<List<Employee>>()
    val employees: LiveData<List<Employee>> = _employees
    private val listenerEmployeesForDepartment: EmployeesForDepartmentListener = {
        _employees.value = it
    }

    private val _departmentname = MutableLiveData<List<DepartmentName>>()
    val departmentname: LiveData<List<DepartmentName>> = _departmentname
    private val listenerDepartmentNames: DepartmentNamesListener = {
        _departmentname.value = it
    }

    fun getRole(): String? {
        return departmentRepository.getRole()
    }

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentDepartmentForViewingPage2Binding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun addAnnouncementsListener() {
        departmentRepository.addListenerAnnouncements(listenerAnnouncements)
    }

    fun addEmployeesListener() {
        departmentRepository.addListenerEmployees(listenerEmployeesForDepartment)
    }

    fun addDepartNamesListener() {
        departmentRepository.addListenerDepartmentNames(listenerDepartmentNames)
    }


    fun getAnnouncements(groupname: String) {
        viewModelScope.launch {
            var res: Response<List<Announcement>> = departmentRepository.getAnnouncements(groupname)
            if (res.isSuccessful){
                listenerAnnouncements(res.body()!!)
                binding.apply {
                    if (announcements.value!!.isEmpty()){
                        Toast.makeText(context, "Объявлений нет пока!", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getGroupname(): String {
        return  departmentRepository.getGroupname()
    }

    fun getUsername(): String? {
        return departmentRepository.getUsername()
    }

    fun createAd(groupname: String, announcement: Announcement) {
        viewModelScope.launch {
            var res: Response<HttpResponse> = departmentRepository.createAd(groupname, announcement)
            if (res.code() == 200){
                Toast.makeText(context, res.body()!!.message, Toast.LENGTH_SHORT).show()
                binding.editMessage.setText("")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setEmotion(announcement: Announcement, emotion: String, username: String, groupname: String) {
        viewModelScope.launch {
            var res: Response<List<Announcement>> = departmentRepository.setEmotion(announcement, emotion, username, groupname)
            if (res.isSuccessful){
                departmentRepository.setAnnouncementsListener(res.body()!!)
                listenerAnnouncements(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getTheEmployeeDepartment(groupname: String) {
        viewModelScope.launch {
            var res: Response<List<Employee>> = departmentRepository.getTheEmployeeDepartment(groupname)
            if (res.isSuccessful){
                departmentRepository.setEmployeesListener(res.body()!!)
                listenerEmployeesForDepartment(res.body()!!)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun removeAnnouncementsListener() {
        departmentRepository.removeListenerAnnouncements(listenerAnnouncements)
    }


    fun removeEmployeesListener() {
        departmentRepository.removeListenerEmployees(listenerEmployeesForDepartment)
    }

    fun removeListenerDepartmentNames() {
        departmentRepository.removeListenerDepartmentNames(listenerDepartmentNames)
    }

    fun getDepartmentName(view: View) {
        viewModelScope.launch {
            var res: Response<List<DepartmentName>> = departmentRepository.getDepartmentName()
            if (res.isSuccessful){
                departmentRepository.setDepartmentNamesListener(res.body()!!)
                listenerDepartmentNames(res.body()!!)
                showPopupMenu(departmentname.value!!, view)
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPopupMenu(departmentNames: List<DepartmentName>, view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val mapDepartNames = mutableMapOf(0 to "zero")
        departmentNames.forEach {
            popupMenu.menu.add(0, it.id.toInt(), Menu.NONE, it.name)
            mapDepartNames[it.id.toInt()] = it.name
        }
        popupMenu.setOnMenuItemClickListener{ item ->
            binding.edFind.setText(mapDepartNames[item.itemId])
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    fun setEmotionForDetails(announcement: Announcement, requireView: View, navigator: Navigator) {
        if (checkValid(announcement)) {
            showPopupMenu2(requireView, announcement, navigator)
        } else Toast.makeText(requireView.context, "Вы уже проголосавали!", Toast.LENGTH_SHORT).show()
    }

    private fun checkValid(announcement: Announcement): Boolean {
        val emotions: List<Emotion> = announcement.emotions.filter {
            it.creator.user.username == departmentRepository.getUsername()!!
        }
        return emotions.isEmpty()
    }

    private fun showPopupMenu2(v: View, announcement: Announcement, navigator: Navigator) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val laugh = announcement.emotions.count { it.emotion == "LAUGH" }
        val good = announcement.emotions.count { it.emotion == "GOOD" }
        val surprise = announcement.emotions.count { it.emotion == "SURPRISE" }
        val sad = announcement.emotions.count { it.emotion == "SAD" }
        val anger = announcement.emotions.count { it.emotion == "ANGER" }
        val fear = announcement.emotions.count { it.emotion == "FEAR" }
        val disgust = announcement.emotions.count { it.emotion == "DISGUST" }
        popupMenu.menu.add(0, Emo.LAUGH, Menu.NONE, "${context.getString(R.string.laugh)} $laugh")
        popupMenu.menu.add(0, Emo.GOOD, Menu.NONE, "${context.getString(R.string.good)} $good")
        popupMenu.menu.add(0, Emo.SURPRISE, Menu.NONE, "${context.getString(R.string.surprise)} $surprise")
        popupMenu.menu.add(0, Emo.SAD, Menu.NONE, "${context.getString(R.string.sad)} $sad")
        popupMenu.menu.add(0, Emo.ANGER, Menu.NONE, "${context.getString(R.string.anger)} $anger")
        popupMenu.menu.add(0, Emo.FEAR, Menu.NONE, "${context.getString(R.string.fear)} $fear")
        popupMenu.menu.add(0, Emo.DISGUST, Menu.NONE, "${context.getString(R.string.disgust)} $disgust")
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                Emo.LAUGH -> {
                    setEmotion(announcement, "LAUGH", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.GOOD -> {
                    setEmotion(announcement, "GOOD", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.SURPRISE -> {
                    setEmotion(announcement, "SURPRISE", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.SAD -> {
                    setEmotion(announcement, "SAD", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.ANGER -> {
                    setEmotion(announcement, "ANGER", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.FEAR -> {
                    setEmotion(announcement, "FEAR", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
                Emo.DISGUST -> {
                    setEmotion(announcement, "DISGUST", departmentRepository.getUsername()!!, departmentRepository.getGroupname())
                    getAnnouncementForUpdate(announcement.id, navigator)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    fun getAnnouncementForUpdate(id: Long, navigator: Navigator) {
        viewModelScope.launch {
            delay(1500)
            val ann: List<Announcement> = announcements.value!!.filter {
                it.id == id
            }.toList()
            navigator.goBack()
            if (ann.isNotEmpty()) navigator.showAnnouncement(ann[0])
        }
    }

}