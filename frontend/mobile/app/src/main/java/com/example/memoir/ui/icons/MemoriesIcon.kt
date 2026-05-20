package com.example.memoir.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.path

private var _MemoriesIcon: ImageVector? = null

val UntitledIcons.MemoriesIcon: ImageVector
    get() {
        if (_MemoriesIcon != null) return _MemoriesIcon!!
        _MemoriesIcon = ImageVector.Builder(
            name = "MemoriesIcon",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // Left page with rounded corners
                moveTo(4f, 3f)
                horizontalLineTo(8f)
                curveTo(10.209f, 3f, 12f, 4.791f, 12f, 7f)
                verticalLineTo(21f)
                curveTo(12f, 19.343f, 10.657f, 18f, 9f, 18f)
                horizontalLineTo(4f)
                curveTo(2.895f, 18f, 2f, 17.105f, 2f, 16f)
                verticalLineTo(5f)
                curveTo(2f, 3.895f, 2.895f, 3f, 4f, 3f)
                close()
            }
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round
            ) {
                // Right page with rounded corners
                moveTo(20f, 3f)
                horizontalLineTo(16f)
                curveTo(13.791f, 3f, 12f, 4.791f, 12f, 7f)
                verticalLineTo(21f)
                curveTo(12f, 19.343f, 13.343f, 18f, 15f, 18f)
                horizontalLineTo(20f)
                curveTo(21.105f, 18f, 22f, 17.105f, 22f, 16f)
                verticalLineTo(5f)
                curveTo(22f, 3.895f, 21.105f, 3f, 20f, 3f)
                close()
            }
        }.build()
        return _MemoriesIcon!!
    }
