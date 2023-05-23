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
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.utils.Emo
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.model.utils.Role
import com.bsuir.recreation_facility.app.repositories.AnnouncementListener
import com.bsuir.recreation_facility.app.repositories.DepartmentRepository
import com.bsuir.recreation_facility.app.repositories.ErrorListener
import com.bsuir.recreation_facility.databinding.FragmentAnnouncementDetailsBinding
import com.bsuir.recreation_facility.databinding.FragmentEmployeeDetailsBinding
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

interface Emotions{
    fun actionEmotion(announcement: Announcement, emo: String)
}


class AnnouncementDetailsViewModel (
    private val departmentRepository: DepartmentRepository = Singletons.departmentRepository
): ViewModel() {

    private val _announcement = MutableLiveData<Announcement>()
    val announcement: LiveData<Announcement> = _announcement
    private val listenerAnnouncement: AnnouncementListener = {
        _announcement.value = it
    }

    private val _err = MutableLiveData<HttpResponse>()
    val err: LiveData<HttpResponse> = _err
    private val listenerError: ErrorListener = {
        _err.value = it
    }

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentAnnouncementDetailsBinding

    fun registerDetails(
        context: Context?,
        activity: FragmentActivity?,
        binding: FragmentAnnouncementDetailsBinding
    ) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun getAnnouncementDetails(id: Long) {
        viewModelScope.launch {
            var res: Response<Announcement> = departmentRepository.getAnnouncement(id)
            if (res.isSuccessful){
                listenerAnnouncement(res.body()!!)
                binding.apply {
                    val user = announcement.value!!.creator.user
                    val ann = announcement.value!!
                    tvAuthor.text = "${user.surname} ${user.name} ${user.patronymic}"
                    tvCreateDate.text = "${ann.dateCreate}"
                    tvAd.text = "${ann.message}"
                    val laugh = ann.emotions.count { it.emotion == "LAUGH" }
                    val good = ann.emotions.count { it.emotion == "GOOD" }
                    val surprise = ann.emotions.count { it.emotion == "SURPRISE" }
                    val sad = ann.emotions.count { it.emotion == "SAD" }
                    val anger = ann.emotions.count { it.emotion == "ANGER" }
                    val fear = ann.emotions.count { it.emotion == "FEAR" }
                    val disgust = ann.emotions.count { it.emotion == "DISGUST" }
                    val see = ann.views.size
                    tvLaugh.text = "${context.getString(R.string.laugh)}: $laugh"
                    tvGood.text = "${context.getString(R.string.good)}: $good"
                    tvSurprise.text = "${context.getString(R.string.surprise)}: $surprise"
                    tvSad.text = "${context.getString(R.string.sad)}: $sad"
                    tvAnger.text = "${context.getString(R.string.anger)}: $anger"
                    tvFear.text = "${context.getString(R.string.fear)}: $fear"
                    tvDisgust.text = "${context.getString(R.string.disgust)}: $disgust"
                    tvSee.text = "Просмотров: $see"
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }
        }
    }


}