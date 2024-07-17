package com.bloodpressure.bloodtracker.bptracker.ui.info

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentInfoDetailBinding
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoDetailFragment : BaseFragment<FragmentInfoDetailBinding>() {
    private lateinit var adapter: InformationAdapter
    private val viewModel: InfoDetailViewModel by viewModels()

    override fun createViewBinding() = FragmentInfoDetailBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initArguments(requireArguments())
    }

    override fun initializeComponent() {
        adapter = InformationAdapter()
        binding.rcItem.setHasFixedSize(false)
        binding.rcItem.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener { requireActivity().supportFragmentManager.popBackStack() }
    }

    override fun initializeData() {
        val data = viewModel.initData(mContext)
        adapter.data = data
    }

    override fun bindView() {
        val title = when (viewModel.type) {
            Utils.KEY_BLOOD_PRESSURE -> mContext.getString(R.string.about_blood_pressure)
            Utils.KEY_BLOOD_SUGAR -> mContext.getString(R.string.about_blood_sugar)
            Utils.KEY_HEART_RATE -> mContext.getString(R.string.about_heart_rate)
            else -> mContext.getString(R.string.about_weight_bmi)
        }

        binding.txtTitle.text = title
    }

}