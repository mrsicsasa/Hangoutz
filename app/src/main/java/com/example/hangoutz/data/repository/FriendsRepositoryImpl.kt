package com.example.hangoutz.data.repository

import com.example.hangoutz.data.models.Friend
import com.example.hangoutz.data.models.ListOfFriends
import com.example.hangoutz.data.remote.FriendsAPI
import com.example.hangoutz.domain.repository.FriendsRepository
import com.example.hangoutz.utils.Constants
import retrofit2.Response
import javax.inject.Inject
import kotlin.error

class FriendsRepositoryImpl @Inject constructor(friendsAPI: FriendsAPI) : FriendsRepository {
    private val api: FriendsAPI = friendsAPI
    override suspend fun getFriendsFromUserId(
        id: String,
        startingWith: String
    ): Response<List<ListOfFriends>> {
        return api.getFriendsFromUserId(id = "eq.$id", startingWith = "ilike.$startingWith*")
    }

    override suspend fun removeFriend(userId: String, friendId: String): Response<Unit> {
        return api.removeFriend(userId = "eq.$userId", friendId = "eq.$friendId")
    }

    override suspend fun getNonFriendsFromUserId(
        id: String,
        startingWith: String
    ): Response<List<Friend>> {
        val friendsResponse = api.getFriendIdsFromUserId(id = "eq.$id")

        var string = ""
        friendsResponse.body()?.forEach { friend ->
            string += friend.friend_id + ","
        }
        try {
            if (friendsResponse.isSuccessful) {
                return api.getNonFriendsFromUserId(
                    id = "not.in.(${string.trim(',')})",
                    startingWith = "ilike.$startingWith*"
                )
            }
        } catch (_: Exception) {
            error(Constants.GENERIC_ERROR_MESSAGE)
        }
        return error(Constants.GENERIC_ERROR_MESSAGE)
    }
}