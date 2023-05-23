package com.bsuir.recreation_facility.app.views.app

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.CabinetRepository
import com.bsuir.recreation_facility.app.repositories.ErrorListener
import com.bsuir.recreation_facility.app.repositories.UserListener
import com.bsuir.recreation_facility.databinding.FragmentUserDetailsBinding
import com.bsuir.recreation_facility.sources.exceptions.EntrantNotFoundException
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import retrofit2.Response

class UserDetailsViewModel (
    private val cabinetRepository: CabinetRepository = Singletons.cabinetRepository
): ViewModel() {



    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user
    private val listenerUser: UserListener = {
        _user.value = it
    }

    private val _err = MutableLiveData<HttpResponse>()
    val err: LiveData<HttpResponse> = _err
    private val listenerError: ErrorListener = {
        _err.value = it
    }

    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentUserDetailsBinding

    fun registerDetails(context: Context?, activity: FragmentActivity?, binding: FragmentUserDetailsBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

    fun getUserDetails(username: String) {
        viewModelScope.launch {
            var res: Response<User> = cabinetRepository.getUserDetails(username)
            if (res.isSuccessful) {
                listenerUser(res.body()!!)
                binding.apply {
                    Glide.with(context)
                        .load(R.drawable.ic_person)
                        .into(photeImageView)
                    nameTV.text = "Имя: ${user.value!!.name}"
                    surnameTV.text = "Фамилия: ${user.value!!.surname}"
                    patronymicTV.text = "Отчество: ${user.value!!.patronymic}"
                    dateOfBirthTV.text = "Дата рождения: ${user.value!!.dateOfBirth}"
                    phoneTV.text = "Телефон: ${user.value!!.phone}"
                    usernameTV.text = "Username: ${user.value!!.username}"
                    emailTV.text = "Email: ${user.value!!.email}"
                }
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listenerError(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java))
                Toast.makeText(context, err.value?.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

}