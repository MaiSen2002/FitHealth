package com.bloodpressure.bloodtracker.bptracker.ui.result

import android.annotation.SuppressLint
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentResultBloodPressureBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.BloodPressureInfoFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp.StatusAdapter
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.edit.EditBloodPressureViewModel
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale


@AndroidEntryPoint
class ResultBloodPressureFragment : BaseFragment<FragmentResultBloodPressureBinding>() {

    private lateinit var adapter: StatusAdapter
    private val viewModel: EditBloodPressureViewModel by viewModels()

    companion object {
        var isFromHistory = false
    }

    override fun createViewBinding() = FragmentResultBloodPressureBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        if (arguments == null) {
            return
        }
        viewModel.initArguments(arguments)
        adapter = StatusAdapter()
        val data = Utils.getBloodPressureInfo(mContext)
        adapter.data = data
        viewModel.data = data

        val layoutManager = GridLayoutManager(requireContext(), 5)
        binding.rcStatus.layoutManager = layoutManager
        binding.rcStatus.setHasFixedSize(true)
        binding.rcStatus.adapter = adapter
    }


    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            if (!isFromHistory) {
                val intentPreview = Intent(requireContext(), HomeActivity::class.java)
                intentPreview.putExtra(Utils.KEY_TRACKER, 0)
                intentPreview.putExtra(Utils.KEY_DATA, Gson().toJson(viewModel.bloodPressure))
                startActivity(intentPreview)
            } else {
                requireActivity().finish()
            }
        }

        binding.btnEdit.setOnSingleClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra(Utils.KEY_TRACKER, 4)
            intent.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.bloodPressure)
            )
            startActivity(intent)
        }

        binding.btnMoreInfo.setOnClickListener {
            if (activity is MainActivity) {
                BloodPressureInfoFragment().pushToScreen(activity as MainActivity)
            } else {
                BloodPressureInfoFragment().pushToScreen(activity as ResultActivity)
            }
        }

        binding.txtViewAll.setOnSingleClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra(Utils.KEY_START_INFO, true)
            startActivity(intent)
        }

        viewModel.bpInfoLiveData.observe(viewLifecycleOwner) {
            bindViewStatus(it)
            adapter.currentId = it.id
        }

        binding.btnGoHome.setOnSingleClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }

    }

    override fun initializeData() {
        viewModel.initData()
    }

    override fun bindView() {
        val data = viewModel.bloodPressure
        Glide.with(requireActivity()).load(Utils.getSrc(data.icon)).into(binding.ivStatus)
        binding.txtStatus.text = data.status
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = format.format(data.date)
        val time = data.time
        binding.txtDateTime.text = "$time - $date"
        binding.txtInfo.text =
            "${mContext.getString(R.string.sys)}: ${data.systolic} \n${
                mContext.getString(R.string.dia)
            }: ${data.diastolic}"
        binding.txtNote.text = data.note
        adapter.currentId = data.id
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewStatus(model: BPInfoDataModel) {
        Glide.with(requireActivity()).load(Utils.getSrc(model.icon)).into(binding.ivStatus)
        binding.txtStatus.text = model.status
        adapter.currentId = model.id
    }

}