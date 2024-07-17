package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure

import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentMoreInfoBinding
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloodPressureInfoFragment : BaseFragment<FragmentMoreInfoBinding>() {

    private lateinit var adapter: BpInfoAdapter
    override fun createViewBinding() = FragmentMoreInfoBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        adapter = BpInfoAdapter()
        adapter.data = Utils.getBloodPressureInfo(mContext)

        binding.rvInfo.setHasFixedSize(true)
        binding.rvInfo.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
        binding.btnGotIt.setOnClickListener { activity?.supportFragmentManager?.popBackStack() }
    }

    override fun bindView() {

    }

}