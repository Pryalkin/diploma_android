package com.bsuir.recreation_facility.app.screens.app.department

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.recreation_facility.app.views.app.DepartmentViewModel
import com.bsuir.recreation_facility.databinding.FragmentGroupBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GroupFragment : Fragment() {

    private lateinit var binding: FragmentGroupBinding
    private val viewModel by viewModels<DepartmentViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentGroupBinding.inflate(inflater)
        val role: String? = viewModel.getRole()
        val pager = binding.depart
        val pageAdapter: FragmentStateAdapter = DepartmentAdapter(requireActivity(), role)
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabLayout2
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Объявления"
                }
                1 -> {
                    tab.text = "Отдел"
                }
                2 -> {
                    tab.text = "Создать"
                }
            }
        }
        tabLayoutMediator.attach()
        return binding.root
    }

}