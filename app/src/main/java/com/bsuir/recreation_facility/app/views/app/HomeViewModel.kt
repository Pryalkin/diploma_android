package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.AnnouncementsListener
import com.bsuir.recreation_facility.app.repositories.DepartmentRepository
import com.bsuir.recreation_facility.app.repositories.ErrorList
import com.bsuir.recreation_facility.databinding.FragmentHomeForViewingPage2Binding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel (
    private val departmentRepository: DepartmentRepository = Singletons.departmentRepository
): ViewModel() {

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentHomeForViewingPage2Binding

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

    fun getRole(): String? {
        return departmentRepository.getRole()
    }

    fun getGroupname(): String {
        return  departmentRepository.getGroupname()
    }

    fun getUsername(): String? {
        return departmentRepository.getUsername()
    }

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentHomeForViewingPage2Binding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun addAnnouncementsListener() {
        departmentRepository.addListenerAnnouncements(listenerAnnouncements)
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

}