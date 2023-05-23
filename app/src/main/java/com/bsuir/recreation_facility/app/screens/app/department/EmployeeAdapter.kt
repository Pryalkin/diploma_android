package com.bsuir.recreation_facility.app.screens.app.department

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.Employee
import com.bsuir.recreation_facility.databinding.ItemEmployeeDepartmentBinding
import com.bumptech.glide.Glide

interface EmployeeActionListener {
    fun onEmployeeDetails(employee: Employee)
    fun onEmployeeAddFriend(employee: Employee)
    fun onEmployeeDeleteFriend(employee: Employee)
    fun onEmployeeMessage(employee: Employee)
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

class EmployeeAdapter(
    private val actionListener: EmployeeActionListener,
    private val username: String
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>(), View.OnClickListener {

    class EmployeeViewHolder(val binding: ItemEmployeeDepartmentBinding): RecyclerView.ViewHolder(binding.root)

    var employees: List<Employee> = emptyList()
        set(newValue) {
            val diffCallback = EmployeeDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEmployeeDepartmentBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = employees[position]
        val context = holder.itemView.context
        if (employee.user.username == username){
            holder.itemView.visibility = View.GONE
        }
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
        val emp = v.tag as Employee
            val employee: Employee = employees.find { e -> e.user.username == username }!!
            if (employee.friends!!.none { e -> e.user.username == emp.user.username })
                popupMenu.menu.add(0, ADD_FRIEND, Menu.NONE, context.getString(R.string.add_friend))
            else
                popupMenu.menu.add(0,
                    DELETE_FRIEND,
                    Menu.NONE,
                    context.getString(R.string.delete_friend))
            popupMenu.menu.add(0, WRITE_MESSAGE, Menu.NONE, context.getString(R.string.write_message))
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ADD_FRIEND -> {
                    actionListener.onEmployeeAddFriend(emp)
                }
                DELETE_FRIEND -> {
                    actionListener.onEmployeeDeleteFriend(emp)
                }
                WRITE_MESSAGE -> {
                    actionListener.onEmployeeMessage(emp)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ADD_FRIEND = 1
        private const val DELETE_FRIEND = 2
        private const val WRITE_MESSAGE = 3
    }

}