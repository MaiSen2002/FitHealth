package com.bloodpressure.bloodtracker.bptracker.ui.setting

import android.content.Intent
import android.widget.Toast
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentLanguageBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setLocale
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import com.bloodpressure.bloodtracker.bptracker.ui.language.LanguageAdapter
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LanguageFragment : BaseFragment<FragmentLanguageBinding>() {

    @Inject
    lateinit var languageAdapter: LanguageAdapter

    override fun createViewBinding() = FragmentLanguageBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        val currentLanguage = preferenceHelper.getString(PreferenceHelper.PREF_CURRENT_LANGUAGE)
            ?: "vi"
        initListLanguage(currentLanguage)
        (activity as MainActivity).setStatusBarBackground(R.color.color_background)
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
            (activity as MainActivity).setStatusBarBackground(R.color.color_blood)
        }
        binding.btnDone.setOnClickListener {
            val selectedLanguage = languageAdapter.data.firstOrNull { it.active }
            if (selectedLanguage == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_select_language),
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                requireActivity().setLocale(selectedLanguage.code)
                preferenceHelper.setString(
                    PreferenceHelper.PREF_CURRENT_LANGUAGE,
                    selectedLanguage.code
                )

                val intent = Intent(requireActivity().baseContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                (activity as MainActivity).setStatusBarBackground(R.color.color_blood)
            }
        }
    }

    private fun initListLanguage(selectedLanguage: String) {
        val languages = Utils.getListItemLanguage(requireContext())
        languages.forEach {
            if (it.code == selectedLanguage) {
                it.active = true
            }
        }
        binding.lvLanguage.adapter = languageAdapter
        languageAdapter.data = languages
    }
}