package com.example.memoir.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.path

private var _SavedIcon: ImageVector? = null

val UntitledIcons.SavedIcon: ImageVector
    get() {
        if (_SavedIcon != null) return _SavedIcon!!
        _SavedIcon = ImageVector.Builder(
            name = "SavedIcon",
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
                moveTo(20.84f, 4.61f)
                curveTo(20.329f, 4.099f, 19.723f, 3.693f, 19.055f, 3.417f)
                curveTo(18.388f, 3.141f, 17.672f, 2.999f, 16.95f, 3f)
                curveTo(15.532f, 3.016f, 14.179f, 3.585f, 13.19f, 4.58f)
                lineTo(12f, 5.77f)
                lineTo(10.81f, 4.58f)
                curveTo(9.821f, 3.585f, 8.468f, 3.016f, 7.05f, 3f)
                curveTo(6.328f, 2.999f, 5.612f, 3.141f, 4.945f, 3.417f)
                curveTo(4.277f, 3.693f, 3.671f, 4.099f, 3.16f, 4.61f)
                curveTo(1.05f, 6.72f, 1.05f, 10.15f, 3.16f, 12.26f)
                lineTo(12f, 21.1f)
                lineTo(20.84f, 12.26f)
                curveTo(22.95f, 10.15f, 22.95f, 6.72f, 20.84f, 4.61f)
                close()
            }
        }.build()
        return _SavedIcon!!
    }
