package com.example.hangoutz.ui.screens.events

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.R
import com.example.hangoutz.data.models.CountOfAcceptedInvitesForEvent
import com.example.hangoutz.ui.theme.Blue
import com.example.hangoutz.ui.theme.GreenMinty
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.textBodyGrayColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCard(
    backgroundColor: Color,
    imageUrl: String,
    title: String,
    place: String,
    date: String,
    getCountOfAcceptedInvitesForEvent: ()->Int,
    isInvited: Boolean = false
) {
    var countOfPeople = remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    Card(
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            .padding(end = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl.isNotEmpty()) {
                    GlideImage(
                        model = "https://zsjxwfjutstrybvltjov.supabase.co/storage/v1/object/public/avatar/${imageUrl}",
                        contentDescription = "da",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(74.dp)
                            .clip(CircleShape)
                            .border(2.dp, Ivory, CircleShape)
                    )
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 22.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "@ ${place}",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight(400),
                        fontSize = 16.sp
                    )
                )
            }
        }
        Box(
            contentAlignment = Alignment.BottomEnd,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(end = 20.dp, bottom = 10.dp)
        ) {
            if (isInvited) {
                Row{
                    InviteRespondButton(
                        backgroundColor = Blue,
                        fontColor = Color.White,
                        title = stringResource(R.string.decline_button_text),
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    InviteRespondButton(
                        backgroundColor = GreenMinty,
                        fontColor = Color.Black,
                        title = stringResource(R.string.accept_button_text),
                        onClick = {}
                    )
                }
            } else {
                Text(
                    text = "${countOfPeople} people going",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = textBodyGrayColor,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
    LaunchedEffect(key1 = true) {
        coroutineScope.launch {
           countOfPeople.value = getCountOfAcceptedInvitesForEvent()
        }
    }
}
