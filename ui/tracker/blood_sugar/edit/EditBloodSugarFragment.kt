package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.edit

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.base.BaseFragment
import com.bloodpressure.bloodtracker.bptracker.databinding.FragmentAddBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.convertDateToString
import com.bloodpressure.bloodtracker.bptracker.extensions.convertToCalendarFromDate
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener
import com.bloodpressure.bloodtracker.bptracker.extensions.toEditable
import com.bloodpressure.bloodtracker.bptracker.extensions.toTimeString
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.BloodSugarAdapter
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.BloodSugarSaveDialog
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.MeasuredPopup
import com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.add.AddBloodSugarViewModel
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class EditBloodSugarFragment() : BaseFragment<FragmentAddBloodSugarBinding>() {
    override fun createViewBinding() = FragmentAddBloodSugarBinding.inflate(layoutInflater)

    private lateinit var adapter: BloodSugarAdapter
    private val viewModel: AddBloodSugarViewModel by viewModels()

    companion object {
        var isEdit = false
        var isFromTracker = true
    }

    override fun initializeComponent() {
        if (arguments == null) {
            return
        }
        viewModel.initArguments(arguments)
        adapter = BloodSugarAdapter()
        binding.rcStatus.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(mContext, 4)
        binding.rcStatus.layoutManager = layoutManager
        binding.rcStatus.adapter = adapter

        adapter.data = Utils.getBloodSugarInfo(mContext)
    }

    override fun initializeEvent() {
        binding.btnBack.setOnSingleClickListener {
            if (isFromTracker) {
                requireActivity().finish()
            } else {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        binding.edtBloodSugar.doAfterTextChanged {
            try {
                viewModel.onValueChange(it.toString().toDouble())
            } catch (e: Exception) {
                Toast.makeText(
                    mContext,
                    mContext.getString(R.string.please_enter_the_correct_format),
                    Toast.LENGTH_SHORT
                ).show()
                return@doAfterTextChanged
            }
        }

        binding.edtNote.doAfterTextChanged {
            viewModel.bloodSugar.note = it.toString()
        }

        binding.btnMeasured.setOnClickListener {
            hideSoftKeyboard(requireActivity())
            MeasuredPopup.show(mContext, it) { measured ->
                binding.btnMeasured.text = Utils.getNameMeasured(mContext, measured)
                viewModel.bloodSugar.measured = measured
            }
        }
        binding.btnSelectedDate.setOnClickListener {
            showDialogPickDate()
        }
        binding.btnSelectedTime.setOnClickListener {
            showDialogPickTime()
        }
        binding.btnSave.setOnClickListener {
            val bloodSugar = viewModel.bloodSugar
            if (bloodSugar.sugarConcentration == 0.0) {
                Toast.makeText(
                    mContext,
                    mContext.getString(R.string.please_enter_sugar_concentration),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (bloodSugar.measured == -1) {
                Toast.makeText(
                    mContext,
                    mContext.getString(R.string.please_enter_measured),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            BloodSugarSaveDialog.open(childFragmentManager, bloodSugar, true) {
                val intent = Intent(requireContext(), ResultActivity::class.java)
                intent.putExtra(Utils.KEY_TRACKER, 1)
                intent.putExtra(
                    Utils.KEY_DATA,
                    Gson().toJson(viewModel.bloodSugar)
                )
                startActivity(intent)
            }
        }

        viewModel.dateLiveData.observe(viewLifecycleOwner) {
            binding.txtDate.text = it.convertDateToString("dd-MM-YYYY")
        }

        viewModel.timeLiveData.observe(viewLifecycleOwner) {
            binding.txtTime.text = it
        }
    }

    override fun initializeData() {
    }

    override fun bindView() {
        if (isEdit) {
            binding.txtTitle.text = getString(R.string.edit_blood_sugar)
        }
        val data = viewModel.bloodSugar
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtBloodSugar.text = data.sugarConcentration.toString().toEditable()
        binding.txtDate.text = format.format(data.date)
        binding.txtTime.text = data.time
        binding.edtNote.setText(data.note)
        binding.btnMeasured.text = Utils.getNameMeasured(mContext, data.measured)
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
        ) { _, _ ->
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
        ) { _, _ -> }
        datePickerDialog.setCancelable(false)
        datePickerDialog.show()
    }

}