package com.example.hangoutz.ui.screens.noInternet

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.utils.connectivityObserver.AndroidConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NoInternetScreenViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    val isConnected = AndroidConnectivityObserver(context)
        .isConnected
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )
}