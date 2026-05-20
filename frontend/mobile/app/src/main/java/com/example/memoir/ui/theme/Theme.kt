package com.example.memoir.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

/**
 * Application theme wrapper that applies Material3 theming.
 *
 * @param content The composable content to be themed.
 */
@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}
