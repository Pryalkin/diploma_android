package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel

class MessageDialog : DialogFragment(){

    private lateinit var et: EditText
    private val viewModel by viewModels<CabinetViewModel>()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.department_dialog, null)
        et = root.findViewById(R.id.department_name_dialog_in)
        var dialog = builder
            .setView(root)
            .setTitle("Введите сообщение")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { d: DialogInterface?, which: Int ->
                    val employeename = requireArguments().getString(ARG_USERNAME_ID)!!
                    val message = et.text.toString()
                    viewModel.createStimulation(employeename, message, navigator())
//                    setFragmentResult("requestKeyMessage", bundleOf("employeename" to employeename, "message" to message))
                    navigator().updateEmployee(employeename, navigator())
                })
            .setNegativeButton("Cancel", null)
            .create();
        return dialog
    }

    companion object {
        private const val ARG_USERNAME_ID = "ARG_USERNAME_ID"
        fun newInstance(employeename: String): MessageDialog{
            val dialog = MessageDialog()
            dialog.arguments = bundleOf ( ARG_USERNAME_ID to employeename)
            return dialog
        }
    }


}