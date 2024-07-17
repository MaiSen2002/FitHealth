package com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.weight_bmi

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentWeightBmiBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.convertToCalendarFromDate
import com.bloodpressure.bloodtracker.bptracker.extensions.pushToScreen
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.weigh_bmi.preview.PreviewWeightBmiFragment
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class WeightBmiFragment : BaseFragment<FragmentWeightBmiBinding>() {
    private val viewModel: WeightBmiViewModel by viewModels()

    override fun createViewBinding() = FragmentWeightBmiBinding.inflate(layoutInflater)

    companion object{
        var isFromTracker = true
    }

    override fun initializeView() {
    }

    override fun initializeEvent() {
        binding.btnBack.setOnClickListener {
            if (isFromTracker) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        binding.edtHeight.doAfterTextChanged {
            try {
                viewModel.onHeightChange(it.toString().toInt())
            } catch (ex: Exception) {
                Toast.makeText(
                    mContext,
                    mContext.getText(R.string.please_enter_the_correct_format),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.edtWeight.doAfterTextChanged {
            try {
                viewModel.onWeightChange(it.toString().toInt())
            } catch (ex: Exception) {
                Toast.makeText(
                    mContext,
                    mContext.getText(R.string.please_enter_the_correct_format),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnMale.setOnClickListener {
            binding.lytMale.isVisible = false
            binding.lytFemale.isVisible = true
            viewModel.onGenderChange(true)
        }

        binding.btnFemale.setOnClickListener {
            binding.lytMale.isVisible = true
            binding.lytFemale.isVisible = false
            viewModel.onGenderChange(false)
        }

        binding.btnSelectedDate.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnSelectedTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.btnCalculate.setOnSingleClickListener {
            if (viewModel.validateData()) {
                Toast.makeText(
                    mContext,
                    mContext.getString(R.string.please_enter_height_and_weight),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnSingleClickListener
            }
            viewModel.calculateBmi(requireContext())
            viewModel.saveData()
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.txtDate.text = it.convertDateToString("dd/MM/yyyy")
        }
        viewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.txtTime.text = it
        }

        viewModel.isSuccessLiveData.observe(viewLifecycleOwner) {
            val fragment = PreviewWeightBmiFragment()
            fragment.arguments = bundleOf(Utils.KEY_WEIGHT_BMI to Gson().toJson(viewModel.bmiData))
            if (requireActivity() is MainActivity) {
                fragment.pushToScreen(activity as MainActivity)
            } else if (requireActivity() is HomeActivity) {
                fragment.pushToScreen(activity as HomeActivity)
            } else {
                fragment.pushToScreen(activity as ResultActivity)
            }
        }
    }

    override fun initializeData() {
        viewModel.initData()
    }

    override fun bindView() {
        val bmiData = viewModel.bmiData
        binding.txtDate.text = bmiData.date.toDateString()
        binding.txtTime.text = bmiData.time
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
        }
        timePickerDialog.setCancelable(false)
        timePickerDialog.show()
    }

}