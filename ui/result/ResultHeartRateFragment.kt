package com.bloodpressure.bloodtracker.bptracker.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentResultHeartRateBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate.AddHeartRateViewModel
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate.HeartRateAdapter
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class ResultHeartRateFragment : BaseFragment<FragmentResultHeartRateBinding>() {

    private lateinit var adapter: HeartRateAdapter
    private val viewModel: AddHeartRateViewModel by viewModels()

    companion object {
        var isEdit = false
        var isFromHistory = false
    }

    override fun createViewBinding() = FragmentResultHeartRateBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        if (arguments == null) {
            return
        }
        viewModel.initArguments(arguments)
        adapter = HeartRateAdapter()
        binding.rcStatus.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcStatus.layoutManager = layoutManager

        adapter.data = Utils.getHeartRateInfo(requireContext())
        binding.rcStatus.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            if (!isFromHistory) {
                val intentPreview = Intent(requireContext(), HomeActivity::class.java)
                intentPreview.putExtra(Utils.KEY_TRACKER, 2)
                startActivity(intentPreview)
            } else {
                requireActivity().finish()
            }
        }

        binding.btnEdit.setOnSingleClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
//            intent.putExtra(Utils.KEY_EDIT,Utils.EDIT_BLOOD_SUGAR)
            intent.putExtra(Utils.KEY_TRACKER, 6)
            intent.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.heartRate)
            )
            startActivity(intent)
        }

        binding.txtViewAll.setOnSingleClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra(Utils.KEY_START_INFO, true)
            startActivity(intent)
        }


        viewModel.isSaveSuccess.observe(viewLifecycleOwner) {
            if (isFromHistory) {
                requireActivity().finish()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        binding.btnGoHome.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

    }

    override fun initializeData() {
//        viewModel.initData(requireContext())
    }

    override fun bindView() {
        if (isEdit) {
            binding.txtTitle.text = getString(R.string.edit_heart_rate)
        }
        bindViewHeartRate()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewHeartRate() {
        val heartRate = viewModel.heartRate
        binding.ivStatus.setImageResource(heartRate.icon)
        binding.txtStatus.text = "${
            Utils.getStatusHeartRate(
                mContext,
                heartRate.status
            )
        } ${mContext.getString(R.string.heart_rate)}"
        binding.txtInfo.text = heartRate.heartRate.toString() + " BPM"

        binding.txtInfo.setTextColor(Color.parseColor(heartRate.color))

        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.format(heartRate.date)
        val time = heartRate.time
        binding.txtDateTime.text = "$time - $date"
    }

}