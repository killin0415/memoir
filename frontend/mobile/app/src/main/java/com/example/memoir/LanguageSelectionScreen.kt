package com.example.memoir

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoir.ui.theme.AppTheme
import com.example.memoir.ui.theme.inter
import com.example.memoir.ui.theme.judson

/**
 * Language Selection screen that allows users to choose their preferred language.
 *
 * @param initialLanguage The initial language selection ("en" or "zh").
 * @param onLanguageSelect Callback invoked when a language is selected with the language code.
 * @param onNextClick Callback invoked when the Next button is clicked.
 * @param modifier Optional modifier for the root composable.
 */
@Composable
fun LanguageSelectionScreen(
    initialLanguage: String = "en",
    onLanguageSelect: (String) -> Unit = {},
    onNextClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedLanguage by remember { mutableStateOf(initialLanguage) }
    val isChinese = selectedLanguage == "zh"

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DesignTokens.colorLanguageSelectionBackground)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 29.dp)
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            LanguageSelectionContent(isChinese = isChinese)

            Spacer(modifier = Modifier.height(32.dp))

            LanguageOptionButtons(
                selectedLanguage = selectedLanguage,
                onLanguageSelect = { languageCode ->
                    selectedLanguage = languageCode
                    onLanguageSelect(languageCode)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            NextButton(
                isChinese = isChinese,
                onClick = onNextClick,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 80.dp)
            )
        }
    }
}

@Composable
private fun LanguageSelectionContent(
    isChinese: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(if (isChinese) R.string.language_selection_subtitle_zh else R.string.language_selection_subtitle),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(if (isChinese) R.string.language_selection_headline_zh else R.string.language_selection_headline),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeHeadline,
                fontWeight = FontWeight.Bold,
                fontFamily = judson,
                lineHeight = 68.sp
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(if (isChinese) R.string.language_selection_description_zh else R.string.language_selection_description),
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
private fun LanguageOptionButtons(
    selectedLanguage: String,
    onLanguageSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        LanguageOptionButton(
            label = stringResource(R.string.language_english),
            isSelected = selectedLanguage == "en",
            onClick = { onLanguageSelect("en") },
            modifier = Modifier.weight(1f)
        )

        LanguageOptionButton(
            label = stringResource(R.string.language_chinese),
            isSelected = selectedLanguage == "zh",
            onClick = { onLanguageSelect("zh") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun LanguageOptionButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(DesignTokens.cornerRadiusLarge)

    Box(
        modifier = modifier
            .height(56.dp)
            .background(
                color = if (isSelected) DesignTokens.colorMaroon else DesignTokens.colorWhite,
                shape = shape
            )
            .then(
                if (!isSelected) {
                    Modifier.border(
                        width = 1.dp,
                        color = DesignTokens.colorBorderGray,
                        shape = shape
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = if (isSelected) DesignTokens.colorWhite else DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )
    }
}

@Composable
private fun NextButton(
    isChinese: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(DesignTokens.cornerRadiusLarge)

    Box(
        modifier = modifier
            .height(66.dp)
            .background(
                color = DesignTokens.colorMaroon,
                shape = shape
            )
            .clip(shape)
            .clickable(onClick = onClick)
            .padding(horizontal = 34.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(if (isChinese) R.string.next_button_zh else R.string.next_button),
                style = TextStyle(
                    fontSize = DesignTokens.fontSizeMedium,
                    fontWeight = FontWeight.Normal,
                    fontFamily = inter,
                    color = DesignTokens.colorWhite
                )
            )

            Image(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = stringResource(R.string.arrow_right_content_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LanguageSelectionScreenPreview() {
    AppTheme {
        LanguageSelectionScreen()
    }
}
