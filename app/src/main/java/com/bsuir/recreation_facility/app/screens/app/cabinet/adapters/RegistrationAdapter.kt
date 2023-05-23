package com.bsuir.recreation_facility.app.screens.app.cabinet

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.databinding.ItemUserBinding
import com.bumptech.glide.Glide

interface RegistrationActionListener {
    fun onUserDelete(user: User)
    fun onUserDetails(user: User)
    fun onUserRegister(user: User)
}

class UserDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}

class RegistrationAdapter (
    private val actionListener: RegistrationActionListener
) : RecyclerView.Adapter<RegistrationAdapter.UserViewHolder>(), View.OnClickListener {

    class UserViewHolder(val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root)

    var users: List<User> = emptyList()

    set(newValue) {
        val diffCallback = UserDiffCallback(field, newValue)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        field = newValue
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.moreImageViewButton.setOnClickListener(this)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = user
            moreImageViewButton.tag = user
            userNameTextView.text = "${user.surname} ${user.name} ${user.patronymic} "
            userEnrollTextView.text = if (user.confirmation) context.getString(R.string.register) else context.getString(R.string.do_not_register)
            Glide.with(photoImageView.context)
                .load(R.drawable.ic_person)
                .into(photoImageView)
        }
    }

    override fun getItemCount(): Int = users.size



    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id){
            R.id.moreImageViewButton -> {
                showPopupMenu(v)
            }
            else -> {
                actionListener.onUserDetails(user)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val user = v.tag as User
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.delete))
        if (!user.confirmation) {
            popupMenu.menu.add(0, ID_REGISTER, Menu.NONE, context.getString(R.string.id_register))
        }
        popupMenu.setOnMenuItemClickListener{
            when (it.itemId){
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
                ID_REGISTER -> {
                    actionListener.onUserRegister(user)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object{
        private const val ID_REMOVE = 1
        private const val ID_REGISTER = 2
    }

}