package com.bloodpressure.bloodtracker.bptracker.ui.language

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivityLanguageBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setLocale
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import com.bloodpressure.bloodtracker.bptracker.ui.intro.IntroActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LanguageActivity : BaseActivity<ActivityLanguageBinding>() {

    @Inject
    lateinit var languageAdapter: LanguageAdapter

    var fromSplash = false
    override fun createViewBinding(layoutInflater: LayoutInflater): ActivityLanguageBinding {
        return ActivityLanguageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fromSplash = intent.getBooleanExtra(Utils.KEY_FROM_SPLASH, false)
    }

    override fun initializeComponent() {
        val currentLanguage = preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)
            ?: "vn"
        initListLanguage(currentLanguage)
    }

    override fun initializeEvent() {
        binding.btnDone.setOnClickListener {
            val selectedLanguage = languageAdapter.data.firstOrNull { it.active }
            if (selectedLanguage == null) {
                Toast.makeText(this, getString(R.string.please_select_language), Toast.LENGTH_SHORT)
                    .show()
            } else {
                setLocale(selectedLanguage.code)
                preferenceHelper.setString(
                    PreferenceHelper.PREF_CURRENT_LANGUAGE,
                    selectedLanguage.code
                )

                val intent = if (fromSplash) {
                    Intent(this, IntroActivity::class.java)
                } else {
                    Intent(this.baseContext, MainActivity::class.java)
                }
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
                finishAffinity()
            }
        }
    }

    private fun initListLanguage(selectedLanguage: String) {
        val languages = Utils.getListItemLanguage(this)
        languages.forEach {
            if (it.code == selectedLanguage) {
                it.active = true
            }
        }
        binding.lvLanguage.adapter = languageAdapter
        languageAdapter.data = languages
    }

    override fun bindView() {
        setStatusBarBackground(R.color.color_background)
    }

}