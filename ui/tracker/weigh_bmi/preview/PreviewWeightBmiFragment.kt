package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentPreviewWeightBmiBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.gone
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.visible
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.info.WeightBmiInfoFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.weight_bmi.WeightBmiFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PreviewWeightBmiFragment : BaseFragment<FragmentPreviewWeightBmiBinding>() {
    private val viewModel: PreviewWeightBmiViewModel by viewModels()
    private lateinit var adapter: BmiInfoAdapter

    override fun createViewBinding() = FragmentPreviewWeightBmiBinding.inflate(layoutInflater)

    companion object {
        var isFromTracker = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments == null) return
        viewModel.initArguments(arguments)
    }

    override fun initializeComponent() {
        adapter = BmiInfoAdapter()
        binding.rcInfo.setHasFixedSize(false)
        adapter.data = Utils.getBmiInfo(mContext)
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

        binding.btnMoreInfo.setOnSingleClickListener {
            openFragment()
        }
        binding.btnRecalculate.setOnSingleClickListener {
            viewModel.bmiData = null
            if (requireActivity() is MainActivity) {
                WeightBmiFragment().pushToScreen(activity as MainActivity)
            } else if (requireActivity() is HomeActivity) {
                WeightBmiFragment().pushToScreen(activity as HomeActivity)
            } else {
                WeightBmiFragment().pushToScreen(activity as ResultActivity)
            }
        }

        binding.btnSave.setOnSingleClickListener {
            viewModel.saveBmi()
            Toast.makeText(requireContext(), "Lưu thành công", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun initializeData() {
        viewModel.initData()
    }

    override fun bindView() {
        if (isFromTracker) {
            binding.btnSave.visible()
            binding.btnRecalculate.visible()
        } else {
            binding.btnSave.gone()
            binding.btnRecalculate.gone()
        }

        val bmiData = viewModel.bmiData!!
        binding.txtBmi.text = String.format("%.2f", bmiData.bmi)
        if (bmiData.bmi == 0.0) {
            binding.btnRecalculate.text = getString(R.string.calculate)
            preferenceHelper.setBoolean(Utils.IS_CALCULATED, false)
        } else {
            binding.btnRecalculate.text = getString(R.string.recalculate)
            preferenceHelper.setBoolean(Utils.IS_CALCULATED, true)
        }

        when (bmiData.bmi) {
            in 0.0..18.4 -> binding.ivInfo.setImageLevel(0)
            in 18.5..24.9 -> binding.ivInfo.setImageLevel(1)
            else -> binding.ivInfo.setImageLevel(2)
        }

        val color = when (bmiData.bmi) {
            in 0.0..18.4 -> "#52AAEE"
            in 18.5..24.9 -> "#00C57E"
            else -> "#FB5555"
        }
        binding.txtBmi.setTextColor(Color.parseColor(color))

        adapter.currentStatus = viewModel.bmiData!!.status
        binding.rcInfo.adapter = adapter
    }

    private fun openFragment() {
        if (requireActivity() is MainActivity) {
            WeightBmiInfoFragment().pushToScreen(activity as MainActivity)
        } else {
            WeightBmiInfoFragment().pushToScreen(activity as HomeActivity)
        }
    }

}