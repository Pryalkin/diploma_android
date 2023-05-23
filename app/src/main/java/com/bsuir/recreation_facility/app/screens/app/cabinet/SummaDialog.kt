package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel

class SummaDialog : DialogFragment(){

    private val viewModel by viewModels<CabinetViewModel>()

    private lateinit var et: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.department_dialog, null)
        et = root.findViewById(R.id.department_name_dialog_in)
        var dialog = builder
            .setView(root)
            .setTitle("Введите сумму")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { d: DialogInterface?, which: Int ->
                    val employeename = requireArguments().getString(ARG_USERNAME_ID)!!
                    val summa = et.text.toString()
                    val sum = summa.toFloat()
                    if (summa.isNullOrEmpty()) {
                        navigator().toast("Вы ничего не ввели. Сумма пуста!")
                    } else if (isNumeric(summa)) {
                        viewModel.setSumma(sum)
                    } else {
                        navigator().toast("Вы ввели неверный формат суммы")
                    }
                })
            .setNegativeButton("Cancel", null)
            .create();
        return dialog
    }

    companion object {
        private const val ARG_USERNAME_ID = "ARG_USERNAME_ID"
        fun newInstance(employeename: String): SummaDialog{
            val dialog = SummaDialog()
            dialog.arguments = bundleOf ( ARG_USERNAME_ID to employeename)
            return dialog
        }
    }

    fun isNumeric(str: String): Boolean {
        return try {
            str.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }


}