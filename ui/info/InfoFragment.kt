package com.bloodpressure.bloodtracker.bptracker.ui.info

import androidx.core.os.bundleOf
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentInfoBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>() {
    private lateinit var adapter: InfoAdapter
    override fun createViewBinding() = FragmentInfoBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        adapter = InfoAdapter()
        binding.rcInfo.setHasFixedSize(true)

        adapter.data = Utils.getInfo(requireContext())
        binding.rcInfo.adapter = adapter
    }

    override fun initializeEvent() {
        adapter.onItemClicked = {
            openItem(it)
        }
    }

    private fun openItem(position: Int) {
        val fragment = InfoDetailFragment()
        val type = when (position) {
            0 -> Utils.KEY_BLOOD_PRESSURE
            1 -> Utils.KEY_BLOOD_SUGAR
            2 -> Utils.KEY_HEART_RATE
            else -> Utils.KEY_WEIGHT_BMI
        }
        fragment.arguments = bundleOf(Utils.KEY_TYPE to type)
        fragment.pushToScreen(requireActivity() as MainActivity)

    }

    override fun isVisibleBottomNavigation() = true

}