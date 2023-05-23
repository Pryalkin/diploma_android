package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.databinding.DepartmentDialogBinding
import kotlinx.coroutines.delay as delay1

class DepartmentNameDialog : DialogFragment(){

    private lateinit var et: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val root = inflater.inflate(R.layout.department_dialog, null)
        et = root.findViewById(R.id.department_name_dialog_in)
        var dialog = builder
            .setView(root)
            .setTitle("Введите отдел")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { d: DialogInterface?, which: Int ->

                    val employeename = requireArguments().getString(ARG_USERNAME_ID)!!
                    val departmentName = et.text.toString()
                    setFragmentResult("requestKey", bundleOf("employeename" to employeename, "departmentName" to departmentName))
                    if (requireArguments().getBoolean(ARG_FLAG_ID)!!){
                        navigator().updateEmployee(employeename, navigator())
                    }
                })
            .setNegativeButton("Cancel", null)
            .create();
        return dialog
    }

    companion object {
        private const val ARG_USERNAME_ID = "ARG_USERNAME_ID"
        private const val ARG_FLAG_ID = "ARG_FLAG_ID"
        fun newInstance(employeename: String, flag: Boolean): DepartmentNameDialog{
            val dialog = DepartmentNameDialog()
            dialog.arguments = bundleOf ( ARG_USERNAME_ID to employeename,  ARG_FLAG_ID to flag)
            return dialog
        }
    }


}