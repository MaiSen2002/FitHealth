package com.bloodpressure.bloodtracker.bptracker.extensions

import androidx.fragment.app.Fragment
import com.bloodpressure.bloodtracker.bptracker.R
import com.bloodpressure.bloodtracker.bptracker.ui.HomeActivity
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import com.bloodpressure.bloodtracker.bptracker.ui.result.ResultActivity

fun Fragment.pushToScreen(activity: HomeActivity) {
    activity.supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.content_frame, this).commit()
}

fun Fragment.pushToScreen(activity: MainActivity) {
    activity.supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.content_frame, this).commit()
}
fun Fragment.pushToScreen(activity: ResultActivity) {
    activity.supportFragmentManager.beginTransaction().addToBackStack(null)
        .replace(R.id.content_frame, this).commit()
}