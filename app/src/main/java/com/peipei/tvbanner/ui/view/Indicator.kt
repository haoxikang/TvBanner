package com.peipei.tvbanner.ui.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Triangle(color: Color,modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // 创建三角形路径
        val trianglePath = Path().apply {
            // 起点：左下角
            moveTo(0f, size.height)
            // 画线到顶部中点
            lineTo(size.width / 2, 0f)
            // 画线到右下角
            lineTo(size.width, size.height)
            // 闭合路径形成三角形
            close()
        }

        // 绘制填充三角形
        drawPath(
            path = trianglePath,
            color = color
        )
    }
}
@Preview
@Composable
fun PreviewTriangle() {
    Triangle(Color.Red)
}