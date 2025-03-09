package com.peipei.tvbanner.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TvPager(pageCount: Int, pagerState: PagerState) {
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 24.dp),
    ) { page ->
        Card(
            modifier = Modifier.padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(getRomDomColor())
            ) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Page ${page % pageCount+1}"
                )
            }
        }
    }
}

private fun getRomDomColor(): Color {
    return Color(
        red = (0..255).random(),
        green = (0..255).random(),
        blue = (0..255).random()
    )
}

@Preview
@Composable
fun PreviewTvPager() {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
    TvPager(10, pagerState)
}