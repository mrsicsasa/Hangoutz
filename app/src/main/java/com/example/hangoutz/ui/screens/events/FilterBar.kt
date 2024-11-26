package com.example.hangoutz.ui.screens.events

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangoutz.ui.theme.Orange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FilterBar(
    pagerState: PagerState,
    tabTitles: List<String> = listOf("GOING", "INVITED", "MINE"),
    scope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = Color(0xFF53576D).copy(
            alpha = 0.46f
        ),
        contentColor = Color.Black,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier.run {
                    tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(42.dp)
                        .width(82.dp)
                        .padding(horizontal = 16.dp)
                        .clip(CircleShape)
                        .background(Orange.copy(alpha = 0.8f))
                }
            )
        },
        divider = {

        },
        modifier = modifier
            //  .padding(horizontal = 16.dp)
            .height(44.dp)
            .clip(CircleShape.copy(CornerSize(20)))
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                    //  viewModel.filterEventsByTab(index)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 13.sp,
                        fontWeight = FontWeight(400),
                        color = if (pagerState.currentPage == index) Color.Black else Orange
                    ),
                    //  modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}