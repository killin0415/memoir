package com.example.memoir

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memoir.ui.theme.AppTheme
import com.example.memoir.ui.theme.inter
import com.example.memoir.ui.theme.judson

/**
 * Welcome screen that displays the introduction to Taiwan cultural journey app.
 *
 * @param selectedLanguage The current language selection ("en" or "zh").
 * @param onBeginTourClick Callback invoked when the user clicks the "Begin your tour" button.
 * @param modifier Optional modifier for the root composable.
 */
@Composable
fun WelcomeScreen(
    selectedLanguage: String = "en",
    onBeginTourClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isChinese = selectedLanguage == "zh"
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DesignTokens.colorFloralWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = DesignTokens.spacingLarge)
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            
            WelcomeContent(isChinese = isChinese)
            
            Spacer(modifier = Modifier.weight(1f))
            
            BeginTourButton(
                isChinese = isChinese,
                onClick = onBeginTourClick,
                modifier = Modifier.padding(bottom = 80.dp)
            )
        }
    }
}

@Composable
private fun WelcomeContent(
    isChinese: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(DesignTokens.spacingMedium),
        modifier = modifier
    ) {
        Text(
            text = stringResource(if (isChinese) R.string.welcome_subtitle_zh else R.string.welcome_subtitle),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )
        
        Text(
            text = stringResource(if (isChinese) R.string.welcome_headline_zh else R.string.welcome_headline),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeHeadline,
                fontWeight = FontWeight.Bold,
                fontFamily = judson
            )
        )
        
        Spacer(modifier = Modifier.height(DesignTokens.spacingSmall))
        
        Text(
            text = stringResource(if (isChinese) R.string.welcome_description_zh else R.string.welcome_description),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeBody,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )
    }
}

@Composable
private fun BeginTourButton(
    isChinese: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = DesignTokens.colorMaroon,
            contentColor = DesignTokens.colorWhite
        ),
        shape = RoundedCornerShape(DesignTokens.cornerRadiusLarge),
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(if (isChinese) R.string.begin_tour_button_zh else R.string.begin_tour_button),
                style = TextStyle(
                    fontSize = DesignTokens.fontSizeMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = inter
                )
            )
            
            Image(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = stringResource(R.string.arrow_right_content_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(14.dp, 15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    AppTheme {
        WelcomeScreen()
    }
}
