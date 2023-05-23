package com.bsuir.recreation_facility.app.views.auth

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsuir.recreation_facility.Singletons
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.HttpResponse
import com.bsuir.recreation_facility.app.repositories.MessListener
import com.bsuir.recreation_facility.app.repositories.UserRepository
import com.bsuir.recreation_facility.databinding.FragmentRegistrationBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response

class RegistrationViewModel(
    private val userRepository: UserRepository = Singletons.userRepository
): ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val listener: MessListener = {
        _message.value = it
    }
    lateinit var context: Context
    lateinit var activity: FragmentActivity
    lateinit var binding: FragmentRegistrationBinding

    fun registration(user: User){
        binding.apply {
            if (edName.text.toString() == ""){
                Toast.makeText(context, "Укажите имя!", Toast.LENGTH_SHORT).show()
            } else if (edSurname.text.toString() == ""){
                Toast.makeText(context, "Укажите фамилию!", Toast.LENGTH_SHORT).show()
            } else if (edPatronymic.text.toString() == ""){
                Toast.makeText(context, "Укажите отчество!", Toast.LENGTH_SHORT).show()
            } else if (edPhone.text.toString() == ""){
                Toast.makeText(context, "Укажите телефон!", Toast.LENGTH_SHORT).show()
            } else if (edEmail.text.toString() == ""){
                Toast.makeText(context, "Укажите email!", Toast.LENGTH_SHORT).show()
            } else if (edUsername.text.toString() == ""){
                Toast.makeText(context, "Укажите username!", Toast.LENGTH_SHORT).show()
            } else {
                viewModelScope.launch {
                    var res: Response<User> = userRepository.registration(user)
                    delay(1000)
                    if (res.isSuccessful){
                        listener("Пользователь успешно зарегистрирован!")
                            edName.setText("")
                            edSurname.setText("")
                            edPatronymic.setText("")
                            edUsername.setText("")
                            edPhone.setText("")
                            edEmail.setText("")
                            dateOfBirth.setText("2000-01-01 00:00:00")
                    } else {
                        val gson = GsonBuilder().setDateFormat("MM-dd-yyyy hh:mm:ss").create()
                        listener(gson.fromJson(res.errorBody()!!.string(), HttpResponse::class.java).message)
                    }
                    Toast.makeText(context, message.value, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun register(context: Context?, activity: FragmentActivity?, binding: FragmentRegistrationBinding) {
        this.context = context!!
        this.activity = activity!!
        this.binding = binding
    }

}