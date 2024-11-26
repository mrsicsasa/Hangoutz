package com.example.hangoutz.ui.screens.friends

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.User
import com.example.hangoutz.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class FriendsUIState(
    val friends: List<User>
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    private val context: Context
) : ViewModel() {
    private lateinit var _uiState: MutableStateFlow<FriendsUIState>
    var uiState: StateFlow<FriendsUIState>

    init {
        initUiState()
        uiState = _uiState
    }

    private fun initUiState() {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                MutableStateFlow(
                    friendsRepository.getFriendsFromUserId(
                        SharedPreferencesManager.getUserId(context).orEmpty()
                    )
                )
            }
            if (response.value.isSuccessful) {
                response.value.body()?.let {
                    if (it.isNotEmpty()) {
                        _uiState.value.copy(friends = it)
                    }
                }
            }
        }
    }
}