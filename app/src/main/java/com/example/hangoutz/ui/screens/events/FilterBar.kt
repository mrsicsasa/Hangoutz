package com.example.hangoutz.ui.screens.events

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hangoutz.ui.theme.Charcoal
import com.example.hangoutz.ui.theme.FilteBarBackground
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.OrangeDark
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
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
    tabHeight: Dp,
    text: String,
    numberOfInvites: Int
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Charcoal
        } else {
            Orange
        },
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .height(tabHeight)
            .padding(top = Dimensions.TAB_TEXT_TOP_PADDING)
    ) {
        if (text == EventsFilterOptions.INVITED.name.uppercase() && numberOfInvites > 0) {
            BadgedBox(
                badge = {
                    Badge(
                        modifier = Modifier
                            .padding(start = Dimensions.BADGE_START_PADDING)
                            .size(Dimensions.BADGE_SIZE),
                        containerColor = OrangeDark
                    ) {
                        Text(
                            text = numberOfInvites.toString(),
                            fontSize = Dimensions.BADGE_FONT_SIZE,
                            color = Charcoal
                        )
                    }
                },
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = text,
                    color = tabTextColor,
                    textAlign = TextAlign.Center,
                    fontSize = if (LocalConfiguration.current.screenWidthDp > Constants.SCREEN_SIZE_THRESHOLD) Dimensions.TAB_TEXT_FONT_SIZE_MEDIUM_SCREEN else Dimensions.TAB_TEXT_FONT_SIZE_SMALL_SCREEN,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        } else {
            Text(
                text = text,
                color = tabTextColor,
                textAlign = TextAlign.Center,
                fontSize = if (LocalConfiguration.current.screenWidthDp > Constants.SCREEN_SIZE_THRESHOLD) Dimensions.TAB_TEXT_FONT_SIZE_MEDIUM_SCREEN else Dimensions.TAB_TEXT_FONT_SIZE_SMALL_SCREEN,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

    }
}

@Composable
fun FilterBar(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    pagerState: PagerState,
    numberOfInvites: Int = 0
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val barWidth = screenWidth * Dimensions.BAR_WIDTH_SCREEN_PERCENT
    val barHeight = screenHeight * Dimensions.BAR_HEIGHT_SCREEN_PERCENT
    val tabWidth = (barWidth - Dimensions.TAB_SPACE_FROM_BAR) / items.size
    val tabHeight = barHeight - Dimensions.TAB_SPACE_FROM_BAR
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )


    Box(
        modifier = modifier
            .height(barHeight)
            .width(barWidth)
            .clip(CircleShape)
            .background(
                FilteBarBackground.copy(
                    alpha = Dimensions.FILTER_BAR_ALPHA
                )
            )
            .height(tabHeight)
            .padding(if (LocalConfiguration.current.screenWidthDp > Constants.SCREEN_SIZE_THRESHOLD) Dimensions.INDICATOR_TAB_TOP_PADDING_MEDIUM_SCREEN else Dimensions.INDICATOR_TAB_TOP_PADDING_SMALL_SCREEN)
            .padding(start = Dimensions.INDICATOR_PADDING_START)
    ) {
        MyTabIndicator(
            indicatorOffset = indicatorOffset,
            indicatorColor = Orange,
            indicatorWidth = tabWidth
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
                    tabWidth = tabWidth,
                    tabHeight = tabHeight,
                    text = text,
                    numberOfInvites = numberOfInvites
                )
            }
        }
    }
}