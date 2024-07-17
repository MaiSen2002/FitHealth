package com.bloodpressure.bloodtracker.bptracker.ui.history

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.databinding.DialogConfirmBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.setOnSingleClickListener

class DeleteConfirmDialog : DialogFragment() {

    private lateinit var binding: DialogConfirmBinding
    private var onButtonClicked: (() -> Unit)? = null

    companion object {
        fun open(manager: FragmentManager, onButtonClicked: () -> Unit) {
            val dialog = com.bloodpressure.bloodtracker.bptracker.ui.history.DeleteConfirmDialog()
            dialog.show(manager, null)
            dialog.onButtonClicked = onButtonClicked
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogConfirmBinding.inflate(layoutInflater)
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
        initializeEvent()
    }

    private fun initializeEvent() {
        binding.btnCancel.setOnSingleClickListener { dismiss() }
        binding.btnDelete.setOnSingleClickListener {
            onButtonClicked?.invoke()
            dismiss()
        }
    }
}