package com.bsuir.recreation_facility.app.screens.app.department

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bsuir.recreation_facility.R
import com.bsuir.recreation_facility.Singletons.navigator
import com.bsuir.recreation_facility.app.model.Announcement
import com.bsuir.recreation_facility.app.model.Emotion
import com.bsuir.recreation_facility.app.model.utils.Emo
import com.bsuir.recreation_facility.app.views.app.AnnouncementDetailsViewModel
import com.bsuir.recreation_facility.app.views.app.DepartmentViewModel
import com.bsuir.recreation_facility.databinding.FragmentAnnouncementDetailsBinding

class AnnouncementDetailsFragment: Fragment() {

    lateinit var binding: FragmentAnnouncementDetailsBinding
    private val viewModel by viewModels<AnnouncementDetailsViewModel>()
    private val viewModel2 by viewModels<DepartmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAnnouncementDetailsBinding.inflate(inflater)
        viewModel.registerDetails(context, activity, binding)
        viewModel.getAnnouncementDetails(requireArguments().getLong(ARG_ANNOUNCEMENT_ID))
        binding.btEmo.setOnClickListener {
            viewModel2.setEmotionForDetails(viewModel.announcement.value!!, requireView(), navigator())

//
//            if (checkValid(viewModel.announcement.value!!)) {
//                showPopupMenu(requireView())
//            } else Toast.makeText(context, "Вы уже проголосавали!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }

    companion object {
        private const val ARG_ANNOUNCEMENT_ID = "ARG_ANNOUNCEMENT_ID"
        fun newInstance(announcement: Announcement): AnnouncementDetailsFragment {
            val fragment = AnnouncementDetailsFragment()
            fragment.arguments = bundleOf ( ARG_ANNOUNCEMENT_ID to announcement.id )
            return fragment
        }
    }

    private fun checkValid(announcement: Announcement): Boolean {
        val emotions: List<Emotion> = announcement.emotions.filter {
            it.creator.user.username == viewModel2.getUsername()
        }
        return emotions.isEmpty()
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val context = v.context
        val announcement = viewModel.announcement.value!!
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
                    viewModel2.setEmotion(announcement, "LAUGH", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.GOOD -> {
                    viewModel2.setEmotion(announcement, "GOOD", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.SURPRISE -> {
                    viewModel2.setEmotion(announcement, "SURPRISE", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.SAD -> {
                    viewModel2.setEmotion(announcement, "SAD", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.ANGER -> {
                    viewModel2.setEmotion(announcement, "ANGER", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.FEAR -> {
                    viewModel2.setEmotion(announcement, "FEAR", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
                Emo.DISGUST -> {
                    viewModel2.setEmotion(announcement, "DISGUST", viewModel2.getUsername()!!, viewModel2.getGroupname())
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }


}