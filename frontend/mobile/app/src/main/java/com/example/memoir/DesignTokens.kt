package com.example.memoir

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Design tokens for consistent styling across the application.
 * Contains color, spacing, typography, and dimension values.
 */
object DesignTokens {
    // region Color Tokens
    val colorWhite: Color = Color(0xFFFFFFFF)
    val colorFloralWhite: Color = Color(0xFFF8F4EB)
    val colorLanguageSelectionBackground: Color = Color(0xFFF7F3EA)
    val colorMaroon: Color = Color(0xFF901913)
    val colorBlack: Color = Color(0xFF000000)
    val colorBorderGray: Color = Color(0xFF8B8B8B)
    // endregion

    // region Typography Tokens
    val fontSizeBody = 16.sp
    val fontSizeMedium = 24.sp
    val fontSizeHeadline = 64.sp
    // endregion

    // region Spacing Tokens
    val spacingSmall = 8.dp
    val spacingMedium = 16.dp
    val spacingLarge = 24.dp
    // endregion

    // region Corner Radius Tokens
    val cornerRadiusLarge = 50.dp
    // endregion
}
