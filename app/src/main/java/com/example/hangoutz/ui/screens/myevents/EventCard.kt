package com.example.hangoutz.ui.screens.myevents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.hangoutz.ui.theme.Blue
import com.example.hangoutz.ui.theme.GreenMinty
import com.example.hangoutz.ui.theme.Ivory
import com.example.hangoutz.ui.theme.textBodyGrayColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun EventCard(
    backgroundColor: Color,
    imageUrl: String,
    title: String,
    place: String,
    date: String,
    isInvited: Boolean = false
) {
    Card(
        colors = CardColors(
            containerColor = backgroundColor,
            contentColor = Color.White,
            disabledContainerColor = Color.Red,
            disabledContentColor = Color.Red
        ),
        shape = RoundedCornerShape(16.dp),
        //  elevation =CardDefaults.cardElevation(10.dp) ,
        modifier = Modifier
            .fillMaxWidth()
            .height(135.dp)
            //  .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(end = 10.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
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
                .padding(end = 20.dp)
        ) {
            if (isInvited) {
                Row(){
                    InviteRespondButton(
                        backgroundColor = Blue,
                        fontColor = Color.White,
                        title = "Decline",
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    InviteRespondButton(
                        backgroundColor = GreenMinty,
                        fontColor = Color.Black,
                        title = "Accept",
                        onClick = {}
                    )
                }
            } else {
                Text(
                    text = "13 people going",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = textBodyGrayColor,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
}
