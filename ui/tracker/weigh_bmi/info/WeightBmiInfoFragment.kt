package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.info

import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentWeightBmiInfoBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview.BmiInfoAdapter
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeightBmiInfoFragment : BaseFragment<FragmentWeightBmiInfoBinding>() {

    private lateinit var adapter: BmiInfoAdapter
    override fun createViewBinding() = FragmentWeightBmiInfoBinding.inflate(layoutInflater)


    override fun initializeComponent() {
        adapter = BmiInfoAdapter()
        binding.rcInfo.setHasFixedSize(false)
        adapter.data = Utils.getBmiInfo(requireContext())
        binding.rcInfo.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener { activity?.supportFragmentManager?.popBackStack() }
        binding.btnGotIt.setOnSingleClickListener { activity?.supportFragmentManager?.popBackStack() }
    }

    override fun bindView() {

    }

}