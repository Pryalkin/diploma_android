package com.bsuir.recreation_facility.app.screens.app.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.screens.app.department.DepartmentAdapter
import com.bsuir.recreation_facility.app.views.app.DepartmentViewModel
import com.bsuir.recreation_facility.app.views.app.HomeViewModel
import com.bsuir.recreation_facility.databinding.FragmentGroupBinding
import com.bsuir.recreation_facility.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        val role: String? = viewModel.getRole()
        val pager = binding.home
        val pageAdapter: FragmentStateAdapter = HomeAdapter(requireActivity(), role)
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabLayout3
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Объявления"
                }
                1 -> {
                    tab.text = "Создать"
                }
            }
        }
        tabLayoutMediator.attach()
        return binding.root
    }

}