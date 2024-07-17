package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.databinding.DialogSaveBloodSugarBinding
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.extensions.toDateString
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BloodSugarSaveDialog : DialogFragment() {
    private val viewModel: BloodSugarSaveViewModel by viewModels()
    private var onButtonClicked: ((bloodSugar: BloodSugar) -> Unit)? = null
    private lateinit var binding: DialogSaveBloodSugarBinding
    private var isEdit = false

    companion object {
        fun open(
            manager: FragmentManager,
            bloodSugar: BloodSugar,
            isEdit: Boolean,
            onButtonClicked: (bloodSugar: BloodSugar) -> Unit
        ) {
            val dialog = BloodSugarSaveDialog()
            val bundle = bundleOf(Utils.KEY_BLOOD_SUGAR to Gson().toJson(bloodSugar))
            dialog.arguments = bundle
            dialog.isEdit = isEdit
            dialog.show(manager, null)
            dialog.onButtonClicked = onButtonClicked
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initArgument(requireArguments())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogSaveBloodSugarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(
            requireContext().resources.getDrawable(
                R.drawable.bg_rounded_12dp,
                null
            )
        )

        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        dialog?.window?.setLayout(width - 64, ViewGroup.LayoutParams.WRAP_CONTENT)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert_NoFloating)

        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeEvents()
        bindView()
    }

    private fun initializeEvents() {
        binding.btnCancel.setOnClickListener { dismiss() }
        binding.btnSave.setOnClickListener {
            if (isEdit) {
                //update blood sugar
                viewModel.updateBloodSugarRecord()
                Toast.makeText(context, "Edit blood sugar", Toast.LENGTH_SHORT).show()
            } else {
                //add blood sugar
                viewModel.addBloodSugarRecord()
                Toast.makeText(context, "Add blood sugar", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.isSaveSuccess.observe(viewLifecycleOwner) {
            onButtonClicked?.invoke(viewModel.bloodSugar)
            dismiss()
        }
    }

    private fun bindView() {
        val bloodSugar = viewModel.bloodSugar
        binding.txtTime.text = "${bloodSugar.date.toDateString()} ${bloodSugar.time}"
        binding.txtBloodSugar.text = bloodSugar.sugarConcentration.toString()
        binding.txtMeasured.text = Utils.getNameMeasured(requireContext(), bloodSugar.measured)
        binding.txtNote.text = bloodSugar.note
        binding.txtBloodSugar.setTextColor(Color.parseColor(getColor(bloodSugar.sugarConcentration)))
    }

    private fun getColor(sugar: Double): String {
        return when (sugar) {
            in 0.0..3.9 -> "#41ACE9"
            in 4.0..5.4 -> "#00C57E"
            in 5.5..6.9 -> "#E9D841"
            else -> "#FB5555"
        }
    }
}