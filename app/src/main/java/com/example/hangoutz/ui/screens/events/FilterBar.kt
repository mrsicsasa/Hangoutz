package com.example.hangoutz.ui.screens.events

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hangoutz.ui.theme.Charcoal
import com.example.hangoutz.ui.theme.FilteBarBackground
import com.example.hangoutz.ui.theme.Orange
import com.example.hangoutz.ui.theme.OrangeDark
import com.example.hangoutz.utils.Constants
import com.example.hangoutz.utils.Dimensions
import com.example.hangoutz.utils.firstLetterUppercase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorHeight: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
    indicatorYOffset: Dp
) {
    Box(
        modifier = Modifier
            .width(
                width = indicatorWidth,
            )
            .height(
                height = indicatorHeight
            )
            .offset(
                x = indicatorOffset,
                y = indicatorYOffset
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            )

    )
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
    numberOfInvites: Int,
    modifier: Modifier
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Charcoal
        } else {
            Orange
        },
        animationSpec = tween(easing = LinearEasing), label = Constants.ANIMATION_TAB_TEXT_COLOR,
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(CircleShape)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = { onClick() }
            )
            .width(tabWidth)
            .fillMaxHeight()
    ) {
        if (text == EventsFilterOptions.INVITED.name.uppercase() && numberOfInvites > 0) {
            Row {
                Text(
                    text = text,
                    color = tabTextColor,
                    textAlign = TextAlign.Center,
                    fontSize = Dimensions.TAB_ITEM_FONT_SIZE,
                )
                Box(contentAlignment = Alignment.Center) {
                    Badge(
                        modifier = Modifier
                            .size(Dimensions.BADGE_SIZE),
                        containerColor = OrangeDark
                    ) {
                        Text(
                            text = numberOfInvites.toString(),
                            fontSize = Dimensions.BADGE_FONT_SIZE,
                            color = Charcoal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

        } else {
            Text(
                text = text,
                color = tabTextColor,
                textAlign = TextAlign.Center,
                fontSize = Dimensions.TAB_ITEM_FONT_SIZE,
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
    numberOfInvites: Int = Constants.NUMBER_OF_INVITES_DEFAULT_VALUE
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp
    val barWidth = screenWidth * Dimensions.BAR_WIDTH_SCREEN_PERCENT
    val barHeight = screenHeight * Dimensions.BAR_HEIGHT_SCREEN_PERCENT
    val tabWidth = barWidth / items.size
    val tabHeight = barHeight * Dimensions.TAB_HEIGHT_PERCENT
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = if (selectedItemIndex == Constants.INVITED_TAB_INDEX) {
            tabWidth * selectedItemIndex + tabWidth * Dimensions.INDICATOR_POSITION_INVITED
        } else {
            (tabWidth * selectedItemIndex + tabWidth * Dimensions.INDICATOR_POSITION_OFFSET)
        },

        animationSpec = tween(easing = LinearEasing), label = Constants.ANIMATION_INDICATOR_OFFSET,
    )
    val indicatorYOffset = barHeight * Dimensions.INDICATOR_Y_OFFSET


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
    ) {
        MyTabIndicator(
            indicatorOffset = indicatorOffset,
            indicatorColor = Orange,
            indicatorWidth = if (selectedItemIndex == Constants.INVITED_TAB_INDEX) tabWidth * Dimensions.INDICATOR_INVITED_WIDTH_PERCENT else tabWidth * Dimensions.INDICATOR_WIDTH_PERCENT,
            indicatorHeight = tabHeight,
            indicatorYOffset = indicatorYOffset
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    index, animationSpec = tween(
                                        Dimensions.SLIDE_ANIMATION_DURATION
                                    )
                                )
                            }
                        }
                    },
                    tabWidth = tabWidth,
                    text = text,
                    numberOfInvites = numberOfInvites,
                    modifier = Modifier.semantics {
                        contentDescription =
                            "${Constants.FILTER_BAR_ITEM}${text.firstLetterUppercase()}"
                    }
                )
            }
        }
    }
}

