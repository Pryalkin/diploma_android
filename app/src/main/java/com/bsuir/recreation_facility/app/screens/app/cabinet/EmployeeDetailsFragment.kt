package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.app.views.app.EmployeeDetailsViewModel
import com.bsuir.recreation_facility.databinding.FragmentEmployeeDetailsBinding
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.Stimulation
import com.bsuir.recreation_facility.app.model.User
import java.util.*


class EmployeeDetailsFragment : Fragment() {

    lateinit var binding: FragmentEmployeeDetailsBinding
    private val viewModel by viewModels<EmployeeDetailsViewModel>()
    private val viewModel2 by viewModels<CabinetViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { key, bundle ->
            val username = viewModel2.getUsername()
            val employeename = bundle.getString("employeename")!!
            val departmentName = bundle.getString("departmentName")!!
            viewModel2.registerAnEmployeeInTheDepartment(username = username!!, employeename = employeename, name = departmentName)
        }
        setFragmentResultListener("requestKeyForJobTitle") { key, bundle ->
            val username = viewModel2.getUsername()
            val employeename = bundle.getString("employeename")!!
            val jobTitle = bundle.getString("jobTitle")!!
            viewModel2.addJobTitle(username = username!!, employeename = employeename, jobTitle = jobTitle)
        }
        setFragmentResultListener("requestKeyMessage") { key, bundle ->
            val username = viewModel2.getUsername()!!
            val employeename = bundle.getString("employeename")!!
            val message = bundle.getString("message")!!
            val summa = viewModel2.getSumma()!!
            val user = User(
                0L, "", "", "",
                Date(), "", "", username, "",
                Date(), Date(), false, isNotLocked = false, isActive = false
            )
            val employee = Employee(0L, user, null, null, null, null, null, null)
            val stimulation = Stimulation(0L, employee, message, summa, Date())
            viewModel2.addStimulation(employeename = employeename, stimulation)
            viewModel2.setSumma(0.0F)
        }
    }



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentEmployeeDetailsBinding.inflate(inflater)
        viewModel.registerDetails(context, activity, binding)
        viewModel.getUserDetails(requireArguments().getString(ARG_EMPLOYEE_ID)!!)

        binding.apply {

            btJobTitle.setOnClickListener {
                val dialog = JobTitleNameDialog.newInstance(requireArguments().getString(ARG_EMPLOYEE_ID)!!, true)
                dialog.show(requireActivity().supportFragmentManager, null)
            }

            btJobTitleDelete.setOnClickListener {
                viewModel2.deleteJobTitle(viewModel2.getUsername()!!,
                    requireArguments().getString(ARG_EMPLOYEE_ID)!!)
                viewModel2.getEmployeeForUpdate(requireArguments().getString(ARG_EMPLOYEE_ID)!!, navigator())
            }

            btDepartment.setOnClickListener {
                val dialog = DepartmentNameDialog.newInstance(requireArguments().getString(ARG_EMPLOYEE_ID)!!, true)
                dialog.show(requireActivity().supportFragmentManager, null)
            }

            btDepartmentDelete.setOnClickListener {
                viewModel2.deleteEmployeeFromDepartment(viewModel2.getUsername()!!, requireArguments().getString(ARG_EMPLOYEE_ID)!!)
                viewModel2.getEmployeeForUpdate(requireArguments().getString(ARG_EMPLOYEE_ID)!!, navigator())
            }

            btDepartmentTake.setOnClickListener {
                viewModel2.registerAnEmployeeInTheDepartment(username = viewModel2.getUsername()!!,
                    employeename = requireArguments().getString(ARG_EMPLOYEE_ID)!!,
                    name = viewModel2.getGroupname())
                viewModel2.getEmployeeForUpdate(requireArguments().getString(ARG_EMPLOYEE_ID)!!, navigator())
            }

            btPositionTrue.setOnClickListener {
                viewModel2.setPosition(username = viewModel2.getUsername()!!,
                    employeename = requireArguments().getString(ARG_EMPLOYEE_ID)!!, true)
                viewModel2.getEmployeeForUpdate(requireArguments().getString(ARG_EMPLOYEE_ID)!!, navigator = navigator())
            }

            btPositionFalse.setOnClickListener {
                viewModel2.setPosition(username = viewModel2.getUsername()!!,
                    employeename = requireArguments().getString(ARG_EMPLOYEE_ID)!!, false)
                viewModel2.getEmployeeForUpdate(requireArguments().getString(ARG_EMPLOYEE_ID)!!, navigator = navigator())
            }

            btStimulation.setOnClickListener {
                val dialogMessage = MessageDialog.newInstance(requireArguments().getString(ARG_EMPLOYEE_ID)!!)
                dialogMessage.show(requireActivity().supportFragmentManager, null)
                val dialogSumma = SummaDialog.newInstance(requireArguments().getString(ARG_EMPLOYEE_ID)!!)
                dialogSumma.show(requireActivity().supportFragmentManager, null)
            }

        }
        return binding.root
    }

    companion object {
        private const val ARG_EMPLOYEE_ID = "ARG_EMPLOYEE_ID"
        fun newInstance(employee: Employee): EmployeeDetailsFragment{
            val fragment = EmployeeDetailsFragment()
            fragment.arguments = bundleOf ( ARG_EMPLOYEE_ID to employee.user.username )
            return fragment
        }
    }

}