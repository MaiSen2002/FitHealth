package com.bloodpressure.bloodtracker.bptracker.ui.tracker.heart_rate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentAddHeartRateBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.convertToCalendarFromDate
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddHeartRateFragment : BaseFragment<FragmentAddHeartRateBinding>() {

    private lateinit var adapter: HeartRateAdapter
    private val viewModel: AddHeartRateViewModel by viewModels()

    companion object {
        var isFromTracker = true
    }

    override fun createViewBinding() = FragmentAddHeartRateBinding.inflate(layoutInflater)

    override fun initializeComponent() {
        adapter = HeartRateAdapter()
        binding.rcStatus.setHasFixedSize(true)

        val layoutManager = GridLayoutManager(requireContext(), 3)
        binding.rcStatus.layoutManager = layoutManager

        adapter.data = Utils.getHeartRateInfo(requireContext())
        binding.rcStatus.adapter = adapter
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnSave.setOnClickListener {
            viewModel.addHeartRate()
        }
        binding.btnSelectedDate.setOnClickListener {
            showDialogPickDate()
        }
        binding.btnSelectedTime.setOnClickListener {
            showDialogPickTime()
        }

        binding.npkBPM.setOnValueChangedListener { _, _, value ->
            viewModel.onValueChange(value, requireContext())
            bindViewHeartRate()
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.txtDate.text = it.convertDateToString("dd-MM-YYYY")
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.txtTime.text = it
        }

        viewModel.isSaveSuccess.observe(viewLifecycleOwner) {
//            requireActivity().supportFragmentManager.popBackStack()
            //Go to result
            val intentResult = Intent(requireContext(), ResultActivity::class.java)
            intentResult.putExtra(Utils.KEY_TRACKER, 2)
            intentResult.putExtra(
                Utils.KEY_DATA,
                Gson().toJson(viewModel.heartRate)
            )
            startActivity(intentResult)
        }
    }

    override fun initializeData() {
        viewModel.initData(requireContext())
    }

    override fun bindView() {
        bindViewHeartRate()
        bindViewBPM()
    }

    private fun bindViewHeartRate() {
        val heartRate = viewModel.heartRate
        binding.ivStatus.setImageResource(heartRate.icon)
        binding.txtStatus.text = "${
            Utils.getStatusHeartRate(
                mContext,
                heartRate.status
            )
        } ${mContext.getString(R.string.heart_rate)}"
        binding.txtInfo.text = heartRate.description

        binding.txtInfo.setTextColor(Color.parseColor(heartRate.color.toString()))

        binding.txtDate.text = heartRate.date.toDateString()
        binding.txtTime.text = heartRate.time
    }

    private fun bindViewBPM() {
        val data = 20..200
        val display = data.map { (it).toString() }
        binding.npkBPM.minValue = 20
        binding.npkBPM.maxValue = 200
        binding.npkBPM.value = 60
        binding.npkBPM.displayedValues = display.toTypedArray()
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

}