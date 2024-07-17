package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.edit

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentEditBloodPressureBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.convertToCalendarFromDate
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.BloodPressureInfoFragment
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp.StatusAdapter
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@AndroidEntryPoint
class EditBloodPressureFragment : BaseFragment<FragmentEditBloodPressureBinding>() {

    private lateinit var adapter: StatusAdapter
    private val viewModel: EditBloodPressureViewModel by viewModels()

    companion object {
        var isFromTracker = true
    }

    override fun createViewBinding() = FragmentEditBloodPressureBinding.inflate(layoutInflater)

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

        initNpkSystolic()
        initNpkDiastolic()
    }


    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            if (isFromTracker) {
                requireActivity().finish()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
        binding.btnMoreInfo.setOnClickListener {
            if (activity is MainActivity) {
                BloodPressureInfoFragment().pushToScreen(activity as MainActivity)
            } else {
                BloodPressureInfoFragment().pushToScreen(activity as HomeActivity)
            }
        }
        binding.btnUpdate.setOnClickListener {
            viewModel.updateBloodPressure(binding.edtNote.text.toString())
        }

        binding.npkSystolic.setOnValueChangedListener { _, _, value ->
            viewModel.onSystolicChange(value)
        }

        binding.npkDiastolic.setOnValueChangedListener { _, _, value ->
            viewModel.onDiastolicChange(value)
        }
        binding.btnSelectedDate.setOnClickListener {
            showDialogPickDate()
        }
        binding.btnSelectedTime.setOnClickListener {
            showDialogPickTime()
        }

        viewModel.bpInfoLiveData.observe(viewLifecycleOwner) {
            bindViewStatus(it)
            adapter.currentId = it.id
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.txtDate.text = it.convertDateToString("dd-MM-YYYY")
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.txtTime.text = it
        }

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner) {
            val intent = Intent(requireContext(), ResultActivity::class.java)
            intent.putExtra(Utils.KEY_TRACKER, 0)
            intent.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.bloodPressure)
            )
            Log.d("dnc", "EDIT: sys after edit: ${viewModel.bloodPressure.systolic}")
            startActivity(intent)
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
        binding.txtDate.text = format.format(data.date)
        binding.txtTime.text = data.time
        binding.edtNote.setText(data.note)
    }

    private fun showDialogPickTime() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.TimePickerTheme,
            { _, hourOfDay, minute ->
                binding.txtTime.text = toTimeString(hourOfDay, minute)
                viewModel.onTimeChange(hourOfDay, minute)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel)
        ) { _, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
            }
        }
        timePickerDialog.setCancelable(false)
        timePickerDialog.show()
    }

    private fun showDialogPickDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
            { _, year, monthOfYear, dayOfMonth ->
                convertToCalendarFromDate(year, monthOfYear, dayOfMonth)?.time?.time?.let { it1 ->
                    viewModel.onDateChange(
                        it1
                    )
                    binding.txtDate.text = it1.convertDateToString("dd/MM/yyyy")
                }
            }, year, month, day
        )
        datePickerDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel)
        ) { _, which ->
            if (which == DialogInterface.BUTTON_NEGATIVE) {
            }
        }
        datePickerDialog.setCancelable(false)
        datePickerDialog.show()
    }

    private fun initNpkSystolic() {
        val data = 20..300
        val display = data.map { (it).toString() }
        binding.npkSystolic.minValue = 20
        binding.npkSystolic.maxValue = 300
        binding.npkSystolic.value = viewModel.bloodPressure.systolic
        binding.npkSystolic.displayedValues = display.toTypedArray()
    }

    private fun initNpkDiastolic() {
        val data = 20..300
        val display = data.map { (it).toString() }
        binding.npkDiastolic.minValue = 20
        binding.npkDiastolic.maxValue = 300
        binding.npkDiastolic.value = viewModel.bloodPressure.diastolic
        binding.npkDiastolic.displayedValues = display.toTypedArray()
    }

    @SuppressLint("SetTextI18n")
    private fun bindViewStatus(model: BPInfoDataModel) {
        Glide.with(requireActivity()).load(Utils.getSrc(model.icon)).into(binding.ivStatus)
        binding.txtStatus.text = model.status
        binding.txtInfo.text =
            "${mContext.getString(R.string.sys)} ${model.systolic} ${model.textIndicator} ${
                mContext.getString(R.string.dia)
            } ${model.diastolic}"
        adapter.currentId = model.id
    }

}