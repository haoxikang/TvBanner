package com.peipei.tvbanner.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun TvTabList(
    pageCount: Int,
    pagerState: PagerState,
    onTouchStateChanged: (isTouching: Boolean) -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        Log.d("TvBanner", "event: ${event.type}")
                        when (event.type) {
                            PointerEventType.Release -> onTouchStateChanged(false)
                            PointerEventType.Exit -> onTouchStateChanged(false)
                            else -> onTouchStateChanged(true)
                        }
                    }
                }
            }
    ) {
        var currentHeight by remember { mutableStateOf(0.dp) }
        HorizontalPager(
            modifier = Modifier
                .onSizeChanged {
                    currentHeight = it.height.dp
                }
                .background(
                    brush = Brush.horizontalGradient(
                        0.0f to Color.White,
                        0.05f to Color.Transparent,
                        0.95f to Color.Transparent,
                        1.0f to Color.White,
                    )
                ),
            contentPadding = PaddingValues(horizontal = maxWidth / 8),
            state = pagerState,
            pageSize = PageSize.Fixed(maxWidth / 4),
        ) { page ->
            var scale by remember { mutableFloatStateOf(1f) }
            var alpha by remember { mutableFloatStateOf(0.8f) }
            Box(contentAlignment = Alignment.BottomCenter) {
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(scale)
                            .alpha(alpha)
                            .graphicsLayer {
                                val pageOffset = (
                                        (pagerState.currentPage + 1 - page) + pagerState
                                            .currentPageOffsetFraction
                                        ).absoluteValue
                                scale = lerp(
                                    start = 1f,
                                    stop = 1.4f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                                alpha = lerp(
                                    start = 0.8f,
                                    stop = 1f,
                                    fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                )
                            },
                        text = "Page ${page % pageCount}",
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp
                    )
                    VerticalScale(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(0.5f)
                            .padding(top = 8.dp),
                        count = pageCount
                    )

                }
            }

        }
        Triangle(
            Color.Red, modifier = Modifier
                .size(6.dp, 20.dp)
                .offset(x = 1.dp)
                .align(Alignment.BottomCenter)
        )
    }
}

@Preview
@Composable
fun PreviewTvTabList() {
    val pagerState = rememberPagerState(
        initialPage = Int.MAX_VALUE / 2,
        pageCount = { Int.MAX_VALUE }
    )
    TvTabList(10, pagerState)
}