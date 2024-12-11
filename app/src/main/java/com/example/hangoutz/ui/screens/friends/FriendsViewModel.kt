package com.example.hangoutz.ui.screens.friends

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hangoutz.data.local.SharedPreferencesManager
import com.example.hangoutz.data.models.Friend
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
import java.util.UUID
import javax.inject.Inject

data class FriendsUIState(
    val searchQuery: String = "",
    val isActive: Boolean = false,
    val isLoading: Boolean = true,
    val listOfFriends: List<ListOfFriends> = emptyList(),
    val showBottomSheet: Boolean = false,
    val isPopupLoading: Boolean = false,
    val popupSearch: String = "",
    val addFriendList: List<Friend> = emptyList(),
    val addedFriendId: UUID = UUID.randomUUID(),
    val isFiltered: Boolean = false
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
            _uiState.value = _uiState.value.copy(
                isFiltered = true,
                isLoading = true
            )
            fetchFriends(isSearching = true)
        } else {
            if (_uiState.value.isFiltered) {
                _uiState.value = _uiState.value.copy(
                    isFiltered = false,
                    isLoading = true
                )
                fetchFriends(isSearching = false)
            }
        }
    }

    private fun fetchFriends(isSearching: Boolean) {
        _uiState.value = _uiState.value.copy(
            listOfFriends = emptyList(),
            isLoading = true
        )

        if (_uiState.value.isLoading) {
            viewModelScope.launch {
                try {
                    val userId = SharedPreferencesManager.getUserId(context)
                    if (userId != null) {
                        val response = withContext(Dispatchers.IO) {
                            if (isSearching) {
                                friendsRepository.getFriendsFromUserId(userId, _uiState.value.searchQuery)
                            } else {
                                friendsRepository.getFriendsFromUserId(userId)
                            }
                        }

                        if (response.isSuccessful) {
                            response.body()?.let { friends ->
                                _uiState.value = _uiState.value.copy(
                                    listOfFriends = friends.sortedBy { friend -> friend.users.name.uppercase() }
                                )
                            }
                        } else {
                            Log.e("FriendsViewModel", "Error fetching friends: ${response.message()}")
                        }
                    } else {
                        Log.e("FriendsViewModel", "User ID is null")
                    }
                } catch (e: Exception) {
                    Log.e("FriendsViewModel", "Error during fetchFriends: ${e.message}")
                } finally {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                }
            }
        }
    }

    fun removeFriend(friendId: UUID) {
        viewModelScope.launch {
            try {
                val userId = SharedPreferencesManager.getUserId(context)
                if (userId != null) {
                    val response = withContext(Dispatchers.IO) {
                        friendsRepository.removeFriend(userId = userId, friendId = friendId.toString())
                        friendsRepository.removeFriend(userId = friendId.toString(), friendId = userId)
                    }

                    if (response?.isSuccessful == true) {
                        if (_uiState.value.searchQuery.length < Constants.MIN_SEARCH_LENGTH) {
                            fetchFriends(false)
                        } else {
                            _uiState.value = _uiState.value.copy(isLoading = true)
                            fetchFriends(true)
                        }
                    } else {
                        Log.e("FriendsViewModel", "Failed to remove friend, response: ${response?.code()}")
                    }
                } else {
                    Log.e("FriendsViewModel", "User ID is null")
                }
            } catch (e: Exception) {
                Log.e("FriendsViewModel", "Error during removeFriend: ${e.message}")
            }
        }
    }

    fun clearSearchInput() {
        _uiState.value = _uiState.value.copy(searchQuery = "", isLoading = true)
        fetchFriends(false)
    }

    fun clearSearchInputPopupScreen() {
        _uiState.value = _uiState.value.copy(popupSearch = "", addFriendList = emptyList())
    }

    fun showSheetState(isShown: Boolean) {
        _uiState.value = _uiState.value.copy(showBottomSheet = isShown)
    }

    fun onPopupSearchInput(newText: String) {
        _uiState.value = _uiState.value.copy(popupSearch = newText, isPopupLoading = true)
        if (_uiState.value.popupSearch.length >= Constants.MIN_SEARCH_LENGTH) {
            fetchNonFriends(isSearching = true)
        } else {
            _uiState.value = _uiState.value.copy(addFriendList = emptyList(), isPopupLoading = false)
        }
    }

    private fun fetchNonFriends(isSearching: Boolean) {
        if (_uiState.value.isPopupLoading) {
            viewModelScope.launch {
                try {
                    val userId = SharedPreferencesManager.getUserId(context)
                    if (userId != null) {
                        val response = withContext(Dispatchers.IO) {
                            if (isSearching) {
                                friendsRepository.getNonFriendsFromUserId(
                                    userId,
                                    _uiState.value.popupSearch,
                                    friendsId = getFriendsId()
                                )
                            } else {
                                friendsRepository.getNonFriendsFromUserId(
                                    userId,
                                    friendsId = getFriendsId()
                                )
                            }
                        }

                        if (response.isSuccessful) {
                            response.body()?.let { users ->
                                _uiState.value = _uiState.value.copy(
                                    addFriendList = users.sortedBy { user -> user.name.uppercase() }
                                )
                            }
                        } else {
                            Log.e("FriendsViewModel", "Error fetching non-friends: ${response.message()}")
                        }
                    } else {
                        Log.e("FriendsViewModel", "User ID is null")
                    }
                } catch (e: Exception) {
                    Log.e("FriendsViewModel", "Error during fetchNonFriends: ${e.message}")
                } finally {
                    _uiState.value = _uiState.value.copy(isPopupLoading = false)
                }
            }
        }
    }

    fun addFriend(id: UUID) {
        _uiState.value = _uiState.value.copy(addedFriendId = id)
        viewModelScope.launch {
            try {
                val userId = SharedPreferencesManager.getUserId(context)
                if (userId != null) {
                    withContext(Dispatchers.IO) {
                        friendsRepository.addFriend(UUID.fromString(userId), id)
                        friendsRepository.addFriend(id, UUID.fromString(userId))
                    }
                    _uiState.value = _uiState.value.copy(isPopupLoading = true)
                    fetchFriends(_uiState.value.searchQuery.length >= Constants.MIN_SEARCH_LENGTH)
                    fetchNonFriends(_uiState.value.popupSearch.length >= Constants.MIN_SEARCH_LENGTH)
                } else {
                    Log.e("FriendsViewModel", "User ID is null")
                }
            } catch (e: Exception) {
                Log.e("FriendsViewModel", "Error adding friend: ${e.message}")
            }
        }
    }

    private suspend fun getFriendsId(): String {
        val userId = SharedPreferencesManager.getUserId(context)
        if (userId != null) {
            val friendsResponse = friendsRepository.getFriendsFromUserId(id = userId)
            val stringBuilder = StringBuilder(userId).append(",")
            friendsResponse.body()?.forEach { friend ->
                stringBuilder.append(friend.users.id).append(",")
            }
            return stringBuilder.toString().substring(0, stringBuilder.length - 1)
        }
        return ""
    }
}
