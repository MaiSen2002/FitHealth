package com.bloodpressure.bloodtracker.bptracker.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.bloodpressure.bloodtracker.bptracker.extensions.hasNetworkConnection
import com.bloodpressure.bloodtracker.bptracker.helper.PreferenceHelper
import com.bloodpressure.bloodtracker.bptracker.ui.main.MainActivity
import javax.inject.Inject

abstract class BaseFragment<T : ViewBinding> : Fragment(), BaseView {
    protected lateinit var binding: T

    abstract fun createViewBinding(): T

    lateinit var mContext: Context
    @Inject
    lateinit var preferenceHelper: PreferenceHelper
    var isReloadAds = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeView()
        initializeComponent()
        initializeEvent()
        initializeData()
        bindView()
        bindViewBottomNavigation()
    }

    override fun initializeView() {

    }

    override fun initializeComponent() {

    }

    override fun initializeEvent() {

    }

    override fun initializeData() {

    }

    override fun bindView() {

    }

    override fun onStop() {
        super.onStop()
        isReloadAds = true
    }

    private fun bindViewBottomNavigation() {
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).isShowBottomNavigation(isVisibleBottomNavigation())
        }
    }

    open fun isVisibleBottomNavigation() = false

    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }
}