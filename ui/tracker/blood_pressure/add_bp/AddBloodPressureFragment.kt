package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.add_bp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentAddBloodPressureBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BPInfoDataModel
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.convertToCalendarFromDate
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_pressure.BloodPressureInfoFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.bumptech.glide.Glide
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddBloodPressureFragment : BaseFragment<FragmentAddBloodPressureBinding>() {

    private lateinit var adapter: StatusAdapter
    private val viewModel: AddBloodPressureViewModel by viewModels()

    override fun createViewBinding() = FragmentAddBloodPressureBinding.inflate(layoutInflater)

    override fun initializeComponent() {
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
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.btnMoreInfo.setOnClickListener {
            if (activity is MainActivity) {
                BloodPressureInfoFragment().pushToScreen(activity as MainActivity)
            } else {
                BloodPressureInfoFragment().pushToScreen(activity as HomeActivity)
            }
        }
        binding.btnSave.setOnClickListener {
            viewModel.saveBloodPressure(requireContext(), binding.edtNote.text.toString())
        }

        binding.npkSystolic.setOnValueChangedListener { _, _, value ->
            viewModel.onSystolicChange(value)
        }

        binding.npkDiastolic.setOnValueChangedListener { _, _, value ->
            viewModel.onDiastolicChange(value)
        }
        binding.btnSelectedDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnSelectedTime.setOnClickListener {
            showTimePickerDialog()
        }

        viewModel.bpInfoLiveData.observe(viewLifecycleOwner) {
            bindViewStatus(it)
            adapter.currentId = it.id
        }

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner) {
//            activity?.supportFragmentManager?.popBackStack()
            //Go to result
            val intentResult = Intent(requireContext(), ResultActivity::class.java)
            intentResult.putExtra(Utils.KEY_TRACKER, 0)
            intentResult.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.bloodPressure)
            )
            startActivity(intentResult)
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.txtDate.text = it.convertDateToString("dd/MM/yyyy")
        }
        viewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.txtTime.text = it
        }

    }

    override fun initializeData() {
        viewModel.initData()
        viewModel.getInfoBloodPressure()
    }

    override fun bindView() {
        val data = viewModel.bloodPressure
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.txtDate.text = format.format(data.date)
        binding.txtTime.text = data.time
        binding.edtNote.setText(data.note)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerTheme,
            { _, year, monthOfYear, dayOfMonth ->
                convertToCalendarFromDate(year, monthOfYear, dayOfMonth)?.time?.time?.let { it1 ->
                    binding.txtDate.text = it1.convertDateToString("dd/MM/yyyy")
                    viewModel.onDateChange(
                        it1
                    )
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

    private fun showTimePickerDialog() {
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
    }

}