package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.app.repositories.CabinetRepository
import com.bsuir.recreation_facility.app.views.app.CabinetViewModel
import com.bsuir.recreation_facility.databinding.ItemEmployeeBinding
import com.bumptech.glide.Glide


interface GroupActionListener {
    fun onEmployeeDetails(employee: Employee)
    fun onEmployeeAddDepartment(employee: Employee)
    fun onEmployeeDeleteDepartment(employee: Employee)
    fun onEmployeeAddJobTitle(employee: Employee)
    fun onEmployeeDeleteJobTitle(employee: Employee)
}

class EmployeeDiffCallback(
    private val oldList: List<Employee>,
    private val newList: List<Employee>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldList[oldItemPosition]
        val newEmployee = newList[newItemPosition]
        return oldEmployee.id == newEmployee.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldList[oldItemPosition]
        val newEmployee = newList[newItemPosition]
        return oldEmployee == newEmployee
    }
}

class GroupAdapter (
    private val actionListener: GroupActionListener
) : RecyclerView.Adapter<GroupAdapter.EmployeeViewHolder>(), View.OnClickListener {

    class EmployeeViewHolder(val binding: ItemEmployeeBinding): RecyclerView.ViewHolder(binding.root)

    var employees: List<Employee> = emptyList()

    set(newValue) {
        val diffCallback = EmployeeDiffCallback(field, newValue)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        field = newValue
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = employee
            moreImageViewButton.tag = employee
            userNameTextView.text = "${employee.user.surname} ${employee.user.name} ${employee.user.patronymic} "
            departmentTextView.text = "Отдел: ${employee.groups?.name.toString()}"
            jobTitleTextView.text = "Должность: ${employee.jobTitle?.name.toString()}"
            Glide.with(photoImageView.context)
                .load(R.drawable.ic_person)
                .into(photoImageView)
        }
    }

    override fun getItemCount(): Int = employees.size

    override fun onClick(v: View) {
        val employee = v.tag as Employee
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onEmployeeDetails(employee)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val employee = v.tag as Employee
        if (employee.groups!!.name == "Свободный"){
            popupMenu.menu.add(0, ADD_DEPARTMENT, Menu.NONE, context.getString(R.string.add_department))
        } else {
            popupMenu.menu.add(0, DELETE_DEPARTMENT, Menu.NONE, context.getString(R.string.delete_department))
        }
        if (employee.jobTitle!!.name == "Свободный"){
            popupMenu.menu.add(0, ADD_JOB_TITLE, Menu.NONE, context.getString(R.string.add_job_title))
        } else {
            popupMenu.menu.add(0, DELETE_JOB_TITLE, Menu.NONE, context.getString(R.string.delete_job_title))
        }
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ADD_DEPARTMENT -> {
                    actionListener.onEmployeeAddDepartment(employee)
                }
                DELETE_DEPARTMENT -> {
                    actionListener.onEmployeeDeleteDepartment(employee)
                }
                ADD_JOB_TITLE -> {
                    actionListener.onEmployeeAddJobTitle(employee)
                }
                DELETE_JOB_TITLE -> {
                    actionListener.onEmployeeDeleteJobTitle(employee)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ADD_DEPARTMENT = 1
        private const val DELETE_DEPARTMENT = 2
        private const val ADD_JOB_TITLE = 3
        private const val DELETE_JOB_TITLE = 4
    }

}