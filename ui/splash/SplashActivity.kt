package com.bloodpressure.bloodtracker.bptracker.ui.splash

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivitySplashBinding
import com.bloodpressure.bloodtracker.bptracker.ui.language.LanguageActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    override fun createViewBinding(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun initializeView() {
        setStatusBarBackground(R.color.color_blood)

        Handler(Looper.myLooper()!!).postDelayed({
            startNextAct()
        }, 3000)
    }

    private fun startNextAct() {
        val intent =
            Intent(this, LanguageActivity::class.java).putExtra(Utils.KEY_FROM_SPLASH, true)
        startActivity(intent)
        finish()
    }
}