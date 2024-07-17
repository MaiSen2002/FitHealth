package com.bloodpressure.bloodtracker.bptracker.ui.main

import android.view.LayoutInflater
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivityMainBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.ui.history.HistoryFragment
import com.bloodpressure.bloodtracker.bptracker.ui.info.InfoFragment
import com.bloodpressure.bloodtracker.bptracker.ui.setting.SettingsFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.TrackerFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun createViewBinding(layoutInflater: LayoutInflater) =
        ActivityMainBinding.inflate(layoutInflater)

    override fun initializeView() {
        setStatusBarBackground(R.color.color_blood)
    }

    override fun initializeComponent() {
        val isInfo: Boolean = intent.getBooleanExtra(Utils.KEY_START_INFO, false)
        if (isInfo) {
            binding.bottomNavigation.selectedItemId = R.id.infoFragment
            InfoFragment().pushToScreen(this@MainActivity)
        } else {
            TrackerFragment().pushToScreen(this@MainActivity)
        }
    }

    override fun initializeEvent() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.historyFragment -> HistoryFragment().pushToScreen(this@MainActivity)
                R.id.infoFragment -> InfoFragment().pushToScreen(this@MainActivity)
                R.id.settingsFragment -> SettingsFragment().pushToScreen(this@MainActivity)
                R.id.trackerFragment -> TrackerFragment().pushToScreen(this@MainActivity)
            }
            true
        }

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                ExitDialog.open(supportFragmentManager) {
                    exitApp()
                }
            }
        })
    }

    fun exitApp() {
        finish()
        finishAffinity()
    }

    override fun bindView() {
        binding.bottomNavigation.menu.apply {
            findItem(R.id.trackerFragment).title =
                getString(R.string.tracker)
            findItem(R.id.historyFragment).title =
                getString(R.string.history)
            findItem(R.id.infoFragment).title = getString(R.string.info)
            findItem(R.id.settingsFragment).title =
                getString(R.string.setting)
        }

    }


    fun isShowBottomNavigation(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }

    override fun onBackPressed() {
        val currentFragment = supportFragmentManager.fragments.last()
        if (currentFragment is TrackerFragment || currentFragment is HistoryFragment || currentFragment is InfoFragment || currentFragment is SettingsFragment) {
            ExitDialog.open(supportFragmentManager) {
                exitApp()
            }
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}