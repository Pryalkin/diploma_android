package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.recreation_facility.app.screens.app.cabinet.adapters.CabinetAdapter
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.databinding.FragmentCabinetBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class CabinetFragment : Fragment() {

    private lateinit var binding: FragmentCabinetBinding
    private val viewModel by viewModels<CabinetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCabinetBinding.inflate(inflater)
        val role: String? = viewModel.getRole()
        val pager = binding.cabinet
        val pageAdapter: FragmentStateAdapter = CabinetAdapter(requireActivity(), role)
        pager.adapter = pageAdapter

        val tabLayout: TabLayout = binding.tabLayout
        val tabLayoutMediator = TabLayoutMediator(tabLayout, pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Инфа"
                }
                1 -> {
                    tab.text = "Переписка"
                }
                2 -> {
                    tab.text = "Группа"
                }
                3 -> {
                    tab.text = "Регистрация"
                }
            }
        }
        tabLayoutMediator.attach()

        return binding.root
    }

}