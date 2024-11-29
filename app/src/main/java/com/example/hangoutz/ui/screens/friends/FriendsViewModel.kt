package com.example.hangoutz.ui.screens.friends

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.ListOfFriends
import com.example.hangoutz.domain.repository.FriendsRepository
import com.example.hangoutz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class FriendsUIState(
    val searchQuery: String = "",
    val isActive: Boolean = false,
    val isLoading: Boolean = true,
    val listOfFriends: List<ListOfFriends> = emptyList()
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private var _uiState = MutableStateFlow(FriendsUIState())
    var uiState: StateFlow<FriendsUIState> = _uiState

    fun onSearchInput(newText: String) {
        _uiState.value = _uiState.value.copy(searchQuery = newText)
        if (_uiState.value.searchQuery.length >= Constants.MIN_SEARCH_LENGTH) {
            fetchFriends(isSearching = true)
        } else {
            fetchFriends(isSearching = false)
        }
    }

    fun fetchFriends(isSearching: Boolean) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                SharedPreferencesManager.getUserId(context)?.let {
                    if (isSearching) {
                        MutableStateFlow(
                            friendsRepository.getFriendsFromUserId(
                                it,
                                _uiState.value.searchQuery
                            )
                        )
                    } else {
                        MutableStateFlow(friendsRepository.getFriendsFromUserId(it))
                    }
                }
            }
            response?.value?.let {
                if (it.isSuccessful) {
                    response.value.body()?.let {
                        _uiState.value =
                            _uiState.value.copy(listOfFriends = it.sortedBy { it.users.name.uppercase() })
                    }
                }
            }
        }
    }

    fun clearSearchInput() {
        _uiState.value = _uiState.value.copy(searchQuery = "")
        fetchFriends(false)
    }

    fun loadFriends(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}