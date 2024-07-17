package com.bloodpressure.bloodtracker.bptracker.ui.setting

import android.content.Intent
import android.net.Uri
import com.bloodpressure.bloodtracker.bptracker.BuildConfig
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentSettingBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingBinding>() {

    private lateinit var adapter: SettingsAdapter

    override fun createViewBinding() = FragmentSettingBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        adapter = SettingsAdapter()
        binding.rcSetting.setHasFixedSize(true)
        binding.rcSetting.adapter = adapter
    }

    override fun initializeEvent() {
        adapter.onItemClicked = {
            when (it) {
                0 -> LanguageFragment().pushToScreen(activity as MainActivity)
                1 -> shareApp()
                2 -> sendEmail()
            }
        }
    }

    override fun initializeData() {
        adapter.data = Utils.getItemSettings(mContext)
    }

    private fun sendEmail() {
        // Create an Intent to send email
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "text/plain"

        // Set the recipient email address
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("thanhtrang17092002@gmail.com"))

        // Set the subject of the email
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")

        // Set the body of the email
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Body of the email")

        startActivity(Intent.createChooser(emailIntent, "Choose an email client"))
    }

    private fun shareApp() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "BloodPressureTracker")
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "https://play.google.com/store/apps/details?id=${BuildConfig.APPLICATION_ID}"
        )
        startActivity(Intent.createChooser(shareIntent, "Share"))
    }

    override fun isVisibleBottomNavigation() = true

}