package com.bloodpressure.bloodtracker.bptracker.ui.intro

import android.content.Intent
import android.view.LayoutInflater
import androidx.viewpager2.widget.ViewPager2
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseActivity
import com.bloodpressure.bloodtracker.bptracker.databinding.ActivityIntroBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.invisible
import com.bloodpressure.bloodtracker.bptracker.extensions.visible
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.indicator.enums.IndicatorSlideMode
import com.bloodpressure.indicator.enums.IndicatorStyle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>() {

    lateinit var introAdapter: IntroAdapter
    private val data = arrayListOf<Triple<Int, String, String>>()


    override fun createViewBinding(layoutInflater: LayoutInflater) =
        ActivityIntroBinding.inflate(layoutInflater)

    override fun initializeView() {
        setStatusBarBackground(R.color.white)
    }

    override fun initializeComponent() {
        data.add(
            Triple(
                R.drawable.img_intro_1,
                getString(R.string.blood_pressure_tools),
                getString(R.string.you_can_track_the_blood_pressure_easily_and_exactly_on_report)
            )
        )
        data.add(
            Triple(
                R.drawable.img_intro_2,
                getString(R.string.graph_and_health_report),
                getString(R.string.see_the_change_of_your_health_in_every)
            )
        )
        data.add(
            Triple(
                R.drawable.img_intro_3,
                getString(R.string.blood_pressura_information),
                getString(R.string.give_you_useful_knowledge_about_blood_pressure)
            )
        )

        introAdapter = IntroAdapter(data)

        binding.viewPager.adapter = introAdapter

        binding.indicator.apply {
            setSlideMode(IndicatorSlideMode.WORM)
            setIndicatorStyle(IndicatorStyle.ROUND_RECT)
            setupWithViewPager(binding.viewPager)
        }
        binding.indicator.setupWithViewPager(binding.viewPager)
    }

    override fun initializeEvent() {
        binding.txtStart.setOnClickListener {
            if (binding.viewPager.currentItem < introAdapter.itemCount - 1) {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun initializeData() {
    }

    override fun bindView() {

    }
}