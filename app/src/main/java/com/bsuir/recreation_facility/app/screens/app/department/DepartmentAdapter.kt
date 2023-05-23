package com.bsuir.recreation_facility.app.screens.app.department

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bsuir.recreation_facility.app.model.utils.Role

class DepartmentAdapter (fragmentActivity: FragmentActivity, var role: String?) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        var count = when (role) {
            Role.ROLE_SUPER_ADMIN.name, Role.ROLE_ADMIN.name, Role.ROLE_MANAGER.name  -> {
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
        return DepartmentForViewingPage2Fragment.newInstance(position)
    }
}