package com.example.hangoutz.ui.screens.friends

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.FriendRoot
import com.example.hangoutz.domain.repository.FriendsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class FriendsUIState(
    val friendWrappers: List<FriendRoot> = emptyList()
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private var _uiState = MutableStateFlow(FriendsUIState())
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
                    _uiState.value = _uiState.value.copy(friendWrappers = it)
                }
            }
        }
    }
}