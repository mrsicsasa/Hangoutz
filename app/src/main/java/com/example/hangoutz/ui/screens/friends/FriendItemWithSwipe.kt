package com.example.hangoutz.ui.screens.friends

import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.hangoutz.R
import com.example.hangoutz.data.models.ListOfFriends


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendItemWithSwipe(
    friend: ListOfFriends,
    modifier: Modifier,
    onRemove: (ListOfFriends) -> Unit
) {
    val context = LocalContext.current
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            when (it) {
                SwipeToDismissBoxValue.StartToEnd -> return@rememberSwipeToDismissBoxState false
                SwipeToDismissBoxValue.EndToStart -> {
                    onRemove(friend)
                    Toast.makeText(
                        context,
                        context.getString(
                            R.string.is_removed_from_the_friend_list,
                            friend.users.name
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
            }
            return@rememberSwipeToDismissBoxState true
        },
    )
    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier,
        backgroundContent = { DismissBackground(dismissState) }
    ) {
        FriendItem(avatar = friend.users.avatar, name = friend.users.name)
    }
}