package com.bsuir.recreation_facility.app.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.UserListener
import com.bsuir.recreation_facility.app.repositories.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RegistrationViewModel(
    private val userRepository: UserRepository = Singletons.userRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val listener: UserListener = {
        _message.value = it
    }

    fun addUser(user: User){
        viewModelScope.launch {
            var res: Response<User> = userRepository.addUser(user)
            if (res.isSuccessful){
                listener("Пользователь успешно зарегистрирован!")
            } else {
                val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                listener(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message)
            }
        }
    }

}