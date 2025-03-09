package com.peipei.tvbanner.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun VerticalScale(count: Int, modifier: Modifier = Modifier) {
    val centerNum = (count) / 2
    Row(modifier = modifier) {
        repeat(count) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(16.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                if (centerNum == it) {
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(16.dp)
                            .background(Color.Black)
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .width(1.dp)
                            .height(8.dp)
                            .background(Color.Black)
                    )
                }

            }

        }
    }
}
@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun PreviewVerticalScale() {
    VerticalScale(10)
}