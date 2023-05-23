package com.bsuir.recreation_facility.app.screens.app.department

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.Emotion
import com.bsuir.recreation_facility.app.model.User
import com.bsuir.recreation_facility.app.model.utils.Emo
import com.bsuir.recreation_facility.databinding.ItemAnnouncementBinding

interface AnnouncementActionListener {
    fun onAnnouncementDetails(announcement: Announcement)
    fun onEmotion(announcement: Announcement, emotion: String)
}

class AnnouncementDiffCallback(
    private val oldList: List<Announcement>,
    private val newList: List<Announcement>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAnnouncement = oldList[oldItemPosition]
        val newAnnouncement = newList[newItemPosition]
        return oldAnnouncement.id == newAnnouncement.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldAnnouncement = oldList[oldItemPosition]
        val newAnnouncement = newList[newItemPosition]
        return oldAnnouncement == newAnnouncement
    }
}

    class AdAdapter (
        private val actionListener: AnnouncementActionListener,
        private val username: String
    ) : RecyclerView.Adapter<AdAdapter.AnnouncementViewHolder>(), View.OnClickListener {

        class AnnouncementViewHolder(val binding: ItemAnnouncementBinding): RecyclerView.ViewHolder(binding.root)

        var announcements: List<Announcement> = emptyList()

            set(newValue) {
                val diffCallback = AnnouncementDiffCallback(field, newValue)
                val diffResult = DiffUtil.calculateDiff(diffCallback)
                field = newValue
                diffResult.dispatchUpdatesTo(this)
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemAnnouncementBinding.inflate(inflater, parent, false)

            binding.root.setOnClickListener(this)
            binding.btEmotion.setOnClickListener(this)

            return AnnouncementViewHolder(binding)
        }

        override fun onBindViewHolder(holder: AnnouncementViewHolder, position: Int) {
            val announcement = announcements[position]
            val context = holder.itemView.context
            with(holder.binding) {
                holder.itemView.tag = announcement
                btEmotion.tag = announcement
                messageForAnnouncement.text = announcement.message
                val user = announcement.creator.user
                createAnnouncement.text = "${user.surname} ${user.name} ${user.patronymic}"
                adCreationDate.text = announcement.dateCreate.toString()
                viewing.text = "Просмотров: ${announcement.views.size}"
                numberOfComments.text = "Комментариев: ${announcement.comments.size}"
                numberOfEmotions.text = announcement.emotions.size.toString()
            }
        }

        override fun getItemCount(): Int = announcements.size

        override fun onClick(v: View) {
            val announcement = v.tag as Announcement
            when (v.id){
                R.id.btEmotion -> {
                    if (checkValid(announcement)) {
                        showPopupMenu(v)
                    } else Toast.makeText(v.context, "Вы уже проголосавали!", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    actionListener.onAnnouncementDetails(announcement)
                }
            }
        }

        private fun checkValid(announcement: Announcement): Boolean {
            val emotions: List<Emotion> = announcement.emotions.filter {
                it.creator.user.username == username
            }
            return emotions.isEmpty()
        }

        private fun showPopupMenu(v: View) {
            val popupMenu = PopupMenu(v.context, v)
            val context = v.context
            val announcement = v.tag as Announcement
            val laugh = announcement.emotions.count { it.emotion == "LAUGH" }
            val good = announcement.emotions.count { it.emotion == "GOOD" }
            val surprise = announcement.emotions.count { it.emotion == "SURPRISE" }
            val sad = announcement.emotions.count { it.emotion == "SAD" }
            val anger = announcement.emotions.count { it.emotion == "ANGER" }
            val fear = announcement.emotions.count { it.emotion == "FEAR" }
            val disgust = announcement.emotions.count { it.emotion == "DISGUST" }
            popupMenu.menu.add(0, Emo.LAUGH, Menu.NONE, "${context.getString(R.string.laugh)} $laugh")
            popupMenu.menu.add(0, Emo.GOOD, Menu.NONE, "${context.getString(R.string.good)} $good")
            popupMenu.menu.add(0, Emo.SURPRISE, Menu.NONE, "${context.getString(R.string.surprise)} $surprise")
            popupMenu.menu.add(0, Emo.SAD, Menu.NONE, "${context.getString(R.string.sad)} $sad")
            popupMenu.menu.add(0, Emo.ANGER, Menu.NONE, "${context.getString(R.string.anger)} $anger")
            popupMenu.menu.add(0, Emo.FEAR, Menu.NONE, "${context.getString(R.string.fear)} $fear")
            popupMenu.menu.add(0, Emo.DISGUST, Menu.NONE, "${context.getString(R.string.disgust)} $disgust")
            popupMenu.setOnMenuItemClickListener{
                when (it.itemId){
                    Emo.LAUGH -> {
                        actionListener.onEmotion(announcement,"LAUGH")
                    }
                    Emo.GOOD -> {
                        actionListener.onEmotion(announcement,"GOOD")
                    }
                    Emo.SURPRISE -> {
                        actionListener.onEmotion(announcement,"SURPRISE")
                    }
                    Emo.SAD -> {
                        actionListener.onEmotion(announcement,"SAD")
                    }
                    Emo.ANGER -> {
                        actionListener.onEmotion(announcement,"ANGER")
                    }
                    Emo.FEAR -> {
                        actionListener.onEmotion(announcement,"FEAR")
                    }
                    Emo.DISGUST -> {
                        actionListener.onEmotion(announcement,"DISGUST")
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
        }

}