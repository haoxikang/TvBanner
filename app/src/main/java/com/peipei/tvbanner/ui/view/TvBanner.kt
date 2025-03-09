package com.peipei.tvbanner.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun TvBanner(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val vibrator = remember { getVibrator(context) }
    var isTouchingTabList = false
    val pagerStateTvPager = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
    val pagerStateTvTabList = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
    LaunchedEffect(pagerStateTvPager) {
        snapshotFlow {
            val (scrollingState, followingState) = if (pagerStateTvPager.isScrollInProgress) {
                pagerStateTvPager to pagerStateTvTabList
            } else if (pagerStateTvTabList.isScrollInProgress) {
                pagerStateTvTabList to pagerStateTvPager
            } else {
                return@snapshotFlow null
            }
            Triple(
                followingState,
                scrollingState.currentPage,
                scrollingState.currentPageOffsetFraction
            )
        }
            .filterNotNull()
            .collect { (followingState, currentPage, currentPageOffsetFraction) ->
                followingState.scrollToPage(
                    page = currentPage,
                    pageOffsetFraction = currentPageOffsetFraction
                )
            }
    }
    LaunchedEffect(pagerStateTvTabList) {
        snapshotFlow {
            pagerStateTvTabList.currentPageOffsetFraction
        }
            .filter {
                isNearFraction(it, 0.005f) && isTouchingTabList
            }
            .throttleFirst(100.milliseconds)
            .collect {
                vibrate(vibrator)
            }
    }

    Column(
        modifier = modifier
    ) {
        TvPager(10, pagerStateTvPager)
        TvTabList(10, pagerStateTvTabList) {
            isTouchingTabList = it
        }
    }
}


@Composable
@Preview
fun PreviewTvBanner() {
    TvBanner()
}