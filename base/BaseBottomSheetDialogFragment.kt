package com.bloodpressure.bloodtracker.bptracker.base

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * [BaseDialogFragment]
 */
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}