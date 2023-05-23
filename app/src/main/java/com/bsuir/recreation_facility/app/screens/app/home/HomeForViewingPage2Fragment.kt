package com.bsuir.recreation_facility.app.screens.app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.*
import com.bsuir.recreation_facility.app.model.utils.DepartmentConst.ADMINISTRATION
import com.bsuir.recreation_facility.app.screens.app.department.AdAdapter
import com.bsuir.recreation_facility.app.screens.app.department.AnnouncementActionListener
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.app.views.app.HomeViewModel
import com.bsuir.recreation_facility.databinding.FragmentHomeForViewingPage2Binding
import java.util.*
import kotlin.properties.Delegates

class HomeForViewingPage2Fragment : Fragment() {

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentHomeForViewingPage2Binding
    private val viewModel by viewModels<HomeViewModel>()
    private val viewModel2 by viewModels<CabinetViewModel>()
    lateinit var adapterAnnouncement: AdAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeForViewingPage2Binding.inflate(inflater)
        viewModel.register(context, activity, binding)


        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false)
                viewModel.getAnnouncements(ADMINISTRATION)
            }
            1 -> {
                customizeScreen(b = false, b1 = true)
                binding.btCreateAd.setOnClickListener {
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
                    viewModel.createAd(ADMINISTRATION, announcement)
                }
            }
        }

        configureTheAdapterForTheAnnouncement()

        return binding.root
    }

    private fun customizeScreen(b: Boolean, b1: Boolean) {
        if (b) binding.seeAd.visibility = View.VISIBLE
        else binding.seeAd.visibility = View.GONE
        if (b1) binding.createAd.visibility = View.VISIBLE
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

        viewModel.announcements.observe(viewLifecycleOwner, androidx.lifecycle.Observer{
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


    companion object {
        @JvmStatic
        fun newInstance(page: Int): HomeForViewingPage2Fragment {
            val fragment: HomeForViewingPage2Fragment = HomeForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }

}