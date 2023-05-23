package com.bsuir.recreation_facility.app.screens.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.screens.app.cabinet.EmployeeDetailsFragment
import com.bsuir.recreation_facility.app.screens.app.cabinet.UserDetailsFragment
import com.bsuir.recreation_facility.app.screens.app.department.AnnouncementDetailsFragment
import com.bsuir.recreation_facility.app.screens.app.department.EmpDetailsFragment
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.databinding.ActivityApplicationBinding

class ApplicationActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityApplicationBinding
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    private val viewModel by viewModels<CabinetViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApplicationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragment_app)
        drawerLayout = binding.drawerLayout
        binding.navigationView.setupWithNavController(navController)

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment_app)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun showInfo(user: User) {
        val fragment = UserDetailsFragment.newInstance(user)
        navController.navigate(R.id.userDetailsFragment, fragment.arguments)
    }

    override fun showAnnouncement(announcement: Announcement) {
        val fragment = AnnouncementDetailsFragment.newInstance(announcement)
        navController.navigate(R.id.announcementDetailsFragment, fragment.arguments)
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun toast(messageRes: String) {
        Toast.makeText(this, messageRes, Toast.LENGTH_SHORT).show()
    }

    override fun showEmployee(employee: Employee) {
        val fragment = EmployeeDetailsFragment.newInstance(employee)
        navController.navigate(R.id.employeeDetailsFragment, fragment.arguments)
    }

    override fun showEmp(employee: Employee) {
        val fragment = EmpDetailsFragment.newInstance(employee)
        navController.navigate(R.id.empDetailsFragment, fragment.arguments)
    }

    override fun updateEmployee(username: String, navigator: Navigator) {
        viewModel.getEmployeeForUpdate(username, navigator)
    }


}