package com.example.hangoutz.ui.components.customIndicator

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangoutz.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            )
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            White
        } else {
            Color.Black
        },
        animationSpec = tween(easing = LinearEasing),
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .height(if (LocalConfiguration.current.screenWidthDp > 400) 48.dp else 38.dp)
            .padding(top = 2.dp),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
        fontSize = if (LocalConfiguration.current.screenWidthDp > 400) 12.sp else 10.sp
    )
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    onClick: (index: Int) -> Unit,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = if (LocalConfiguration.current.screenWidthDp > 400) 120.dp * selectedItemIndex else 100.dp * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .width(if (LocalConfiguration.current.screenWidthDp > 400) 120.dp * items.size +10.dp else 100.dp * items.size +10.dp)
            .clip(CircleShape)
            .background(
                Color(0xFF53576D75).copy(
                    alpha = 0.46f
                )
            )
            .height(if (LocalConfiguration.current.screenWidthDp > 400) 40.dp else 30.dp)
            //  .padding(8.dp)
            .padding(if (LocalConfiguration.current.screenWidthDp > 400) 5.dp else 0.dp)
    ) {
        MyTabIndicator(
            indicatorWidth = if (LocalConfiguration.current.screenWidthDp > 400) 120.dp else 100.dp,
            indicatorOffset = indicatorOffset,
            indicatorColor = Orange,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(index) }
                    },
                    tabWidth = if (LocalConfiguration.current.screenWidthDp > 400) 120.dp else 100.dp,
                    text = text,
                )
            }
        }
    }
}