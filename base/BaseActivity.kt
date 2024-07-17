package com.bloodpressure.bloodtracker.bptracker.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import java.util.Locale
import javax.inject.Inject

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity(), BaseView {
    protected lateinit var binding: T

    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    var isReloadAds = false

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        super.onCreate(savedInstanceState)
        setContentView(getInflatedLayout(layoutInflater))

        val currentLanguage = preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)
        val locale = currentLanguage?.let { Locale(currentLanguage) }
        val config = resources.configuration

        locale?.let {
            config.setLocale(it)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                createConfigurationContext(config)
            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
        }

        initializeView()
        initializeComponent()
        initializeEvent()
        initializeData()
        bindView()
    }

    abstract fun createViewBinding(layoutInflater: LayoutInflater): T

    private fun getInflatedLayout(inflater: LayoutInflater): View {
        binding = createViewBinding(inflater)
        return binding.root
    }

    override fun initializeView() {

    }

    override fun initializeComponent() {

    }

    override fun initializeEvent() {

    }

    override fun initializeData() {

    }

    override fun bindView() {

    }

    override fun onStop() {
        super.onStop()
        isReloadAds = true
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            fullScreenImmersive(window)
        }
    }

    private fun fullScreenImmersive(window: Window?) {
        if (window != null) {
            fullScreenImmersive(window.decorView)
        }
    }

    private fun fullScreenImmersive(view: View) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(window, view).let { controller ->
//            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.hide(WindowInsetsCompat.Type.navigationBars())
        }
    }

    fun setStatusBarBackground(@DrawableRes background: Int) {
        val window: Window = window
        val drawable = ContextCompat.getDrawable(this, background)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.navigationBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.setBackgroundDrawable(drawable)
    }

}