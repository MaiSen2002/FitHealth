package com.bloodpressure.bloodtracker.bptracker.ui.main

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.databinding.DialogExitBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener

class ExitDialog : DialogFragment() {

    private lateinit var binding: DialogExitBinding
    private var onExitSelected: (() -> Unit)? = null

    companion object {
        fun open(manager: FragmentManager, onExitSelected: (() -> Unit)? = null) {
            val dialog = ExitDialog()
            dialog.onExitSelected = onExitSelected
            dialog.show(manager, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogExitBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initEvents()
    }

    private fun initEvents() {
        binding.btnCancel.setOnSingleClickListener { dismiss() }
        binding.btnExit.setOnSingleClickListener { onExitSelected?.invoke() }
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

}