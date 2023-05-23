package com.bsuir.recreation_facility.app.screens.app.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.*
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.app.views.app.DepartmentViewModel
import com.bsuir.recreation_facility.databinding.FragmentDepartmentForViewingPage2Binding
import java.util.*
import kotlin.properties.Delegates

class DepartmentForViewingPage2Fragment : Fragment(){

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentDepartmentForViewingPage2Binding
    private val viewModel by viewModels<DepartmentViewModel>()
    private val viewModel2 by viewModels<CabinetViewModel>()
    lateinit var adapterAnnouncement: AdAdapter
    lateinit var adapterEmployee: EmployeeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDepartmentForViewingPage2Binding.inflate(inflater)
        viewModel.register(context, activity, binding)
        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false, b2 = false)
                viewModel.getAnnouncements(viewModel.getGroupname())
            }
            1 -> {
                customizeScreen(b = false, b1 = true, b2 = false)
                viewModel.getTheEmployeeDepartment(viewModel.getGroupname())
                binding.btFind.setOnClickListener {
                    if (binding.edFind.text.toString().isEmpty()) {
                        viewModel.getTheEmployeeDepartment(viewModel.getGroupname())
                    } else {
                        viewModel.getTheEmployeeDepartment(binding.edFind.text.toString())
                    }
                }
                binding.btHelp.setOnClickListener {
                    viewModel.getDepartmentName(requireView())
                }
                viewModel.addDepartNamesListener()
            }
            2 -> {
                customizeScreen(b = false, b1 = false, b2 = true)
                binding.btCreateAd.setOnClickListener {
                    val groupname = viewModel.getGroupname()
                    val user = User(
                        0L, "", "", "",
                        Date(), "", "", viewModel.getUsername()!!, "",
                        Date(), Date(), false, isNotLocked = false, isActive = false
                    )
                    val emotions: List<Emotion> = emptyList()
                    val comments: List<Comment> = emptyList()
                    val views: List<Viewing> = emptyList()
                    val creator = Employee(0L, user, null, null, null, null, null, null)
                    val announcement = Announcement(0L, binding.editMessage.text.toString(),creator,
                        emotions, comments, views, Date())
                    viewModel.createAd(groupname, announcement)
                }
            }
        }

        configureTheAdapterForTheAnnouncement()
        configureTheAdapterForTheEmployee()

        return binding.root
    }

    private fun customizeScreen(b: Boolean, b1: Boolean, b2: Boolean) {
        if (b) binding.seeAd.visibility = View.VISIBLE
        else binding.seeAd.visibility = View.GONE
        if (b1) binding.seeYourDepartment.visibility = View.VISIBLE
        else binding.seeYourDepartment.visibility = View.GONE
        if (b2) binding.createAd.visibility = View.VISIBLE
        else binding.createAd.visibility = View.GONE
    }

    private fun configureTheAdapterForTheAnnouncement() {
        adapterAnnouncement = AdAdapter(object  : AnnouncementActionListener {
            override fun onAnnouncementDetails(announcement: Announcement) {
                navigator().showAnnouncement(announcement)
            }
            override fun onEmotion(announcement: Announcement, emotion: String) {
                viewModel.setEmotion(announcement, emotion, viewModel.getUsername()!!, viewModel.getGroupname()!!)
            }
        }, viewModel.getUsername()!!)

        viewModel.announcements.observe(viewLifecycleOwner, Observer{
            adapterAnnouncement.announcements = it
        })

        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewAnnouncement.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewAnnouncement.adapter = adapterAnnouncement

        val itemAnimatorAnnouncement = binding.recyclerViewAnnouncement.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }

        viewModel.addAnnouncementsListener()
    }

    private fun configureTheAdapterForTheEmployee() {
        adapterEmployee = EmployeeAdapter(object  : EmployeeActionListener {
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

        viewModel.employees.observe(viewLifecycleOwner, Observer{
            adapterEmployee.employees = it
        })

        val layoutManagerEmployee = LinearLayoutManager(context)
        binding.recyclerViewEmp.layoutManager = layoutManagerEmployee
        binding.recyclerViewEmp.adapter = adapterEmployee

        val itemAnimatorEmployee = binding.recyclerViewEmp.itemAnimator
        if (itemAnimatorEmployee is DefaultItemAnimator){
            itemAnimatorEmployee.supportsChangeAnimations = false
        }

        viewModel.addEmployeesListener()
    }

    companion object {
        @JvmStatic
        fun newInstance(page: Int): DepartmentForViewingPage2Fragment {
            val fragment: DepartmentForViewingPage2Fragment = DepartmentForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeAnnouncementsListener()
        viewModel.removeEmployeesListener()
        viewModel.removeListenerDepartmentNames()
    }
}