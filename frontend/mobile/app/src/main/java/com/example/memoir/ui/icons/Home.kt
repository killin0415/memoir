package com.example.memoir.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.path

private var _Home: ImageVector? = null

val UntitledIcons.Home: ImageVector
    get() {
        if (_Home != null) return _Home!!
        _Home = ImageVector.Builder(
            name = "Home",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 2f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                fill = SolidColor(Color.Transparent)
            ) {
                // Untitled UI Home icon path
                moveTo(9f, 22f)
                verticalLineTo(12f)
                horizontalLineTo(15f)
                verticalLineTo(22f)
                moveTo(2f, 9.5f)
                lineTo(12f, 2f)
                lineTo(22f, 9.5f)
                verticalLineTo(21f)
                curveTo(22f, 21.55f, 21.55f, 22f, 21f, 22f)
                horizontalLineTo(3f)
                curveTo(2.45f, 22f, 2f, 21.55f, 2f, 21f)
                verticalLineTo(9.5f)
                close()
            }
        }.build()
        return _Home!!
    }

