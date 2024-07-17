package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar.preview

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodpressure.bloodtracker.bptracker.domain.BloodSugar
import com.bloodpressure.bloodtracker.bptracker.repository.AppRepository
import com.bloodpressure.bloodtracker.bptracker.util.Utils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class PreviewBloodSugarViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {
    var bloodSugar: BloodSugar? = null

    private val _dataLiveData: MutableLiveData<List<BloodSugar>> = MutableLiveData()
    val dataLiveData: LiveData<List<BloodSugar>>
        get() = _dataLiveData

    fun initArgument(bundle: Bundle?) {
        val result = bundle?.getString(Utils.KEY_BLOOD_SUGAR)
        result?.let {
            bloodSugar = Gson().fromJson(result, BloodSugar::class.java)
        }
    }

    fun getData() {
        viewModelScope.launch {
            val data = ArrayList(repository.getRecentBloodSugar())
            _dataLiveData.postValue(data)
        }
    }

    fun initData() {
        if (bloodSugar != null) return
        runBlocking {
            bloodSugar = repository.getNewestBloodSugar()
        }
    }
}