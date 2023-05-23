package com.bsuir.recreation_facility.app.screens.app.cabinet.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.recreation_facility.app.model.utils.Role
import com.bsuir.recreation_facility.app.screens.app.cabinet.CabinetForViewingPage2Fragment

class CabinetAdapter(fragmentActivity: FragmentActivity, var role: String?) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        var count = when (role) {
            Role.ROLE_SUPER_ADMIN.name -> {
                4
            }
            Role.ROLE_ADMIN.name -> {
                3
            }
            Role.ROLE_MANAGER.name -> {
                3
            }
            Role.ROLE_USER.name -> {
                2
            }
            else -> {0}
        }
        return count
    }

    override fun createFragment(position: Int): Fragment {
        return CabinetForViewingPage2Fragment.newInstance(position)
    }
}