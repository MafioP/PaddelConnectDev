package com.uva.padelconnect.modelView.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    private val isDarkModeEnabled = MutableLiveData<Boolean>()

    fun getIsDarkModeEnabled(): LiveData<Boolean> = isDarkModeEnabled

    fun setDarkModeEnabled(isEnabled: Boolean) {
        isDarkModeEnabled.value = isEnabled
    }
}