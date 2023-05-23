package com.bsuir.recreation_facility.app.screens.app.department

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.screens.app.cabinet.EmployeeDetailsFragment
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.app.views.app.EmpDetailsViewModel
import com.bsuir.recreation_facility.app.views.app.EmployeeDetailsViewModel
import com.bsuir.recreation_facility.databinding.FragmentEmpDetailsBinding
import com.bsuir.recreation_facility.databinding.FragmentEmployeeDetailsBinding

class EmpDetailsFragment : Fragment() {

    lateinit var binding: FragmentEmpDetailsBinding
    private val viewModel by viewModels<EmpDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEmpDetailsBinding.inflate(inflater)
        viewModel.registerDetails(context, activity, binding)
        viewModel.getEmployee(requireArguments().getString(ARG_EMPLOYEE_ID)!!)
        return binding.root
    }

    companion object {
        private const val ARG_EMPLOYEE_ID = "ARG_EMPLOYEE_ID"
        fun newInstance(employee: Employee): EmpDetailsFragment {
            val fragment = EmpDetailsFragment()
            fragment.arguments = bundleOf ( ARG_EMPLOYEE_ID to employee.user.username )
            return fragment
        }
    }
}