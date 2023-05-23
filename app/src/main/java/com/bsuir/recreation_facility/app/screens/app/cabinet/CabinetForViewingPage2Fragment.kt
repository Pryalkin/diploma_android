package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.Role
import com.bsuir.recreation_facility.app.screens.app.department.EmployeeActionListener
import com.bsuir.recreation_facility.app.screens.app.department.EmployeeAdapter
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.databinding.FragmentCabinetForViewingPage2Binding
import kotlin.properties.Delegates

class CabinetForViewingPage2Fragment : Fragment() {

    private lateinit var binding: FragmentCabinetForViewingPage2Binding
    private var pageNumber by Delegates.notNull<Int>()
    private val viewModel by viewModels<CabinetViewModel>()
    lateinit var adapterUser: RegistrationAdapter
    lateinit var adapterEmployee: GroupAdapter
    lateinit var adapterEmp: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetForViewingPage2Binding.inflate(inflater)
        viewModel.register(context, activity, binding)
        setFragmentResultListener("requestKey") { key, bundle ->
            val username = viewModel.getUsername()
            val employeename = bundle.getString("employeename")!!
            val departmentName = bundle.getString("departmentName")!!
            viewModel.registerAnEmployeeInTheDepartment(username = username!!, employeename = employeename, name = departmentName)
        }
        setFragmentResultListener("requestKeyForJobTitle") { key, bundle ->
            val username = viewModel.getUsername()
            val employeename = bundle.getString("employeename")!!
            val jobTitle = bundle.getString("jobTitle")!!
            viewModel.addJobTitle(username = username!!, employeename = employeename, jobTitle = jobTitle)
        }
        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false, b2 = false, b3 = false)
                viewModel.getEmployee()
            }
            1 -> {
                customizeScreen(b = false, b1 = true, b2 = false, b3 = false)
                binding.btCorrespondence.visibility = View.INVISIBLE
                binding.btFindFriend.visibility = View.VISIBLE
                binding.recyclerViewFriends.visibility = View.GONE
                viewModel.getCorrespondence(viewModel.getUsername()!!)
                binding.btCorrespondence.setOnClickListener {
                    viewModel.getCorrespondence(viewModel.getUsername()!!)
                    binding.btCorrespondence.visibility = View.INVISIBLE
                    binding.btFindFriend.visibility = View.VISIBLE
                    binding.recyclerViewFriends.visibility = View.GONE
                    binding.recyclerViewCorrespondence.visibility = View.VISIBLE
                }
                binding.btFindFriend.setOnClickListener {
                    viewModel.getFriends(viewModel.getUsername()!!)
                    binding.btCorrespondence.visibility = View.VISIBLE
                    binding.btFindFriend.visibility = View.INVISIBLE
                    binding.recyclerViewFriends.visibility = View.VISIBLE
                    binding.recyclerViewCorrespondence.visibility = View.GONE
                }
            }
            2 -> {
                customizeScreen(b = false, b1 = false, b2 = true, b3 = false)
                viewModel.getAListOfUnregisteredEmployees()
            }
            3 -> {
                customizeScreen(b = false, b1 = false, b2 = false, b3 = true)
                viewModel.getAListOfUnregisteredUsers()
            }
        }

        configureTheAdapterForTheUser()
        configureTheAdapterForTheEmployee()
        configureTheAdapterForTheFriends()

        return binding.root
    }

    private fun configureTheAdapterForTheFriends() {
        adapterEmp = EmployeeAdapter(object : EmployeeActionListener {
            override fun onEmployeeDetails(employee: Employee) {
                navigator().showEmp(employee)
            }

            override fun onEmployeeAddFriend(employee: Employee) {
                TODO("Not yet implemented")
            }

            override fun onEmployeeDeleteFriend(employee: Employee) {
                TODO("Not yet implemented")
            }

            override fun onEmployeeMessage(employee: Employee) {
                TODO("Not yet implemented")
            }
        }, viewModel.getUsername()!!)

        viewModel.friends.observe(viewLifecycleOwner, Observer{
            adapterEmp.employees = it
        })

        val layoutManagerEmployee = LinearLayoutManager(context)
        binding.recyclerViewFriends.layoutManager = layoutManagerEmployee
        binding.recyclerViewFriends.adapter = adapterEmp

        val itemAnimatorEmployee = binding.recyclerViewFriends.itemAnimator
        if (itemAnimatorEmployee is DefaultItemAnimator){
            itemAnimatorEmployee.supportsChangeAnimations = false
        }

        viewModel.addListenerFriends()
    }

    private fun configureTheAdapterForTheEmployee() {
        adapterEmployee = GroupAdapter(object  : GroupActionListener{
            override fun onEmployeeDetails(employee: Employee) {
                navigator().showEmployee(employee)
            }
            override fun onEmployeeAddDepartment(employee: Employee){
                when (viewModel.getRole()){
                    Role.ROLE_SUPER_ADMIN.name, Role.ROLE_ADMIN.name -> {
                        showDialogForDepartment(employee)
                    }
                    Role.ROLE_MANAGER.name-> {
                        viewModel.registerAnEmployeeInTheDepartment(username = viewModel.getUsername()!!,
                            employeename = employee.user.username,
                            name = viewModel.getGroupname())
                    }
                }
            }
            override fun onEmployeeDeleteDepartment(employee: Employee) {
                viewModel.deleteEmployeeFromDepartment(viewModel.getUsername()!!, employee.user.username)
            }

            override fun onEmployeeAddJobTitle(employee: Employee) {
                showDialogForJobTitle(employee)
            }

            override fun onEmployeeDeleteJobTitle(employee: Employee) {
                viewModel.deleteJobTitle(viewModel.getUsername()!!,
                    employee.user.username)
            }
        })

        viewModel.employees.observe(viewLifecycleOwner, Observer{
            adapterEmployee.employees = it
        })

        val layoutManagerEmployee = LinearLayoutManager(context)
        binding.recyclerViewEmployee.layoutManager = layoutManagerEmployee
        binding.recyclerViewEmployee.adapter = adapterEmployee

        val itemAnimatorEmployee = binding.recyclerViewEmployee.itemAnimator
        if (itemAnimatorEmployee is DefaultItemAnimator){
            itemAnimatorEmployee.supportsChangeAnimations = false
        }

        viewModel.addEmployeesListener()
    }

    private fun showDialogForDepartment(employee: Employee) {
        val dialog = DepartmentNameDialog.newInstance(employee.user.username, false)
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun showDialogForJobTitle(employee: Employee) {
        val dialog = JobTitleNameDialog.newInstance(employee.user.username, false)
        dialog.show(requireActivity().supportFragmentManager, null)
    }

    private fun configureTheAdapterForTheUser() {
        adapterUser = RegistrationAdapter(object : RegistrationActionListener{
            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user.username, null)
            }

            override fun onUserDetails(user: User) {
                navigator().showInfo(user)
            }

            override fun onUserRegister(user: User) {
                viewModel.registerEmployee(user.username, null)
            }

        })

        viewModel.users.observe(viewLifecycleOwner, Observer{
            adapterUser.users = it
        })

        val layoutManagerUser = LinearLayoutManager(context)
        binding.recyclerViewUser.layoutManager = layoutManagerUser
        binding.recyclerViewUser.adapter = adapterUser

        val itemAnimatorUser = binding.recyclerViewUser.itemAnimator
        if (itemAnimatorUser is DefaultItemAnimator){
            itemAnimatorUser.supportsChangeAnimations = false
        }

        viewModel.addUsersListener()
    }

    private fun customizeScreen(b: Boolean, b1: Boolean, b2: Boolean, b3: Boolean) {
        if (b) binding.info.visibility = View.VISIBLE
        else binding.info.visibility = View.GONE
        if (b1) binding.corresponding.visibility = View.VISIBLE
        else binding.corresponding.visibility = View.GONE
        if (b2) binding.department.visibility = View.VISIBLE
        else binding.department.visibility = View.GONE
        if (b3) binding.registration.visibility = View.VISIBLE
        else binding.registration.visibility = View.GONE
    }

    companion object {
        @JvmStatic
        fun newInstance(page: Int): CabinetForViewingPage2Fragment {
            val fragment: CabinetForViewingPage2Fragment = CabinetForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeUsersListener()
        viewModel.removeEmployeesListener()
        viewModel.removeRoomsListener()
        viewModel.removeFriendsListener()
    }


}

