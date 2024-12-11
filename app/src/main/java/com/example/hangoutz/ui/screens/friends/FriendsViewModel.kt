package com.example.hangoutz.ui.screens.friends

import android.content.Context
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
                        response.value.body()?.let { friends ->
                            _uiState.value =
                                _uiState.value.copy(listOfFriends = friends.sortedBy { friend -> friend.users.name.uppercase() })
                        }
                    }
                }
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun removeFriend(friendId: UUID) {
        viewModelScope.launch {
            val response = withContext(Dispatchers.IO) {
                SharedPreferencesManager.getUserId(context)?.let {
                    friendsRepository.removeFriend(userId = it, friendId = friendId.toString())
                    friendsRepository.removeFriend(userId = friendId.toString(), friendId = it)
                }
            }
            if (response?.isSuccessful == true) {
                if (_uiState.value.searchQuery.length < Constants.MIN_SEARCH_LENGTH) {
                    fetchFriends(false)
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = true
                    )
                    fetchFriends(true)
                }
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
            _uiState.value =
                _uiState.value.copy(addFriendList = emptyList(), isPopupLoading = false)
        }
    }

    private fun fetchNonFriends(isSearching: Boolean) {
        if (_uiState.value.isPopupLoading) {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    SharedPreferencesManager.getUserId(context)?.let {
                        if (isSearching) {
                            MutableStateFlow(
                                friendsRepository.getNonFriendsFromUserId(
                                    it,
                                    _uiState.value.popupSearch,
                                    friendsId = getFriendsId()
                                )
                            )
                        } else {
                            MutableStateFlow(
                                friendsRepository.getNonFriendsFromUserId(
                                    it,
                                    friendsId = getFriendsId()
                                )
                            )
                        }
                    }
                }
                response?.value?.let {
                    if (it.isSuccessful) {
                        response.value.body()?.let { users ->
                            _uiState.value =
                                _uiState.value.copy(addFriendList = users.sortedBy { user -> user.name.uppercase() })
                        }
                    }
                }
                _uiState.value = _uiState.value.copy(isPopupLoading = false)
            }
        }
    }

    fun addFriend(id: UUID) {
        _uiState.value =
            _uiState.value.copy(addedFriendId = id)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                MutableStateFlow(
                    friendsRepository.addFriend(
                        UUID.fromString(SharedPreferencesManager.getUserId(context)),
                        id
                    )
                )
            }
            withContext(Dispatchers.IO) {
                MutableStateFlow(
                    friendsRepository.addFriend(
                        id,
                        UUID.fromString(SharedPreferencesManager.getUserId(context))
                    )
                )
            }
            _uiState.value = _uiState.value.copy(isPopupLoading = true)
            fetchFriends(_uiState.value.searchQuery.length >= Constants.MIN_SEARCH_LENGTH)
            fetchNonFriends(_uiState.value.popupSearch.length >= Constants.MIN_SEARCH_LENGTH)
        }
    }

    private suspend fun getFriendsId(): String {
        SharedPreferencesManager.getUserId(context)?.let {
            val friendsResponse = friendsRepository.getFriendsFromUserId(id = it)
            val stringBuilder = StringBuilder(it).append(",")
            friendsResponse.body()?.forEach { friend ->
                stringBuilder.append(friend.users.id).append(",")
            }
            return stringBuilder.toString().substring(0, stringBuilder.length - 1)
        }
        return ""
    }
}