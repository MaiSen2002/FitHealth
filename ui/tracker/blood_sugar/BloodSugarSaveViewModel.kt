package com.bloodpressure.bloodtracker.bptracker.ui.tracker.blood_sugar

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
import javax.inject.Inject

@HiltViewModel
class BloodSugarSaveViewModel @Inject constructor(private val repository: AppRepository) :
    ViewModel() {
    lateinit var bloodSugar: BloodSugar
    private val _isSaveSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isSaveSuccess: LiveData<Boolean>
        get() = _isSaveSuccess

    fun initArgument(bundle: Bundle) {
        val result = bundle.getString(Utils.KEY_BLOOD_SUGAR)
        bloodSugar = Gson().fromJson(result, BloodSugar::class.java)
    }

    fun addBloodSugarRecord() {
        viewModelScope.launch {
            repository.addBloodSugar(bloodSugar)
            bloodSugar = repository.getNewestBloodSugar()
            _isSaveSuccess.postValue(true)
        }
    }

    fun updateBloodSugarRecord() {
        viewModelScope.launch {
            repository.updateBloodSugar(bloodSugar)
            _isSaveSuccess.postValue(true)
        }
    }
}