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
import androidx.compose.ui.graphics.Color
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

data class CultureInterest(
    val id: String,
    val nameResourceId: Int
)

/**
 * Culture Interest Selection screen that allows users to choose their cultural interests.
 *
 * @param selectedLanguage The current language selection ("en" or "zh").
 * @param onInterestSelect Callback invoked when an interest is toggled.
 * @param onStartExploringClick Callback invoked when the Start Exploring button is clicked.
 * @param onSkipClick Callback invoked when the Skip button is clicked.
 * @param modifier Optional modifier for the root composable.
 */
@Composable
fun CultureInterestScreen(
    selectedLanguage: String = "en",
    initialInterests: Set<String> = emptySet(),
    onInterestSelect: (String, Boolean) -> Unit = { _, _ -> },
    onStartExploringClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isChinese = selectedLanguage == "zh"
    
    val interests = remember {
        listOf(
            CultureInterest("temples", R.string.culture_temples),
            CultureInterest("old_streets", R.string.culture_old_streets),
            CultureInterest("architecture", R.string.culture_architecture),
            CultureInterest("trade", R.string.culture_trade),
            CultureInterest("colonial", R.string.culture_colonial),
            CultureInterest("crafts", R.string.culture_crafts)
        )
    }

    var selectedInterests by remember { mutableStateOf(initialInterests) }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 14.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                SkipButton(isChinese = isChinese, onClick = onSkipClick)
            }

            Spacer(modifier = Modifier.height(120.dp))

            CultureSelectionContent(isChinese = isChinese)

            Spacer(modifier = Modifier.height(32.dp))

            InterestOptionsList(
                interests = interests,
                selectedInterests = selectedInterests,
                isChinese = isChinese,
                onInterestToggle = { interestId, isSelected ->
                    selectedInterests = if (isSelected) {
                        selectedInterests + interestId
                    } else {
                        selectedInterests - interestId
                    }
                    onInterestSelect(interestId, isSelected)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                StartExploringButton(isChinese = isChinese, onClick = onStartExploringClick)
            }
        }
    }
}

@Composable
private fun CultureSelectionContent(
    isChinese: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(if (isChinese) R.string.culture_interest_subtitle_zh else R.string.culture_interest_subtitle),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeMedium,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(if (isChinese) R.string.culture_interest_headline_zh else R.string.culture_interest_headline),
            color = DesignTokens.colorBlack,
            style = TextStyle(
                fontSize = DesignTokens.fontSizeHeadline,
                fontWeight = FontWeight.Bold,
                fontFamily = judson,
                lineHeight = 68.sp
            )
        )
    }
}

@Composable
private fun InterestOptionsList(
    interests: List<CultureInterest>,
    selectedInterests: Set<String>,
    isChinese: Boolean,
    onInterestToggle: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        interests.forEach { interest ->
            // In a real app, you'd have translated strings for each interest.
            // For now, we use the existing ones as they contain both EN and ZH in the resource name logic.
            // But wait, the resource IDs are for EN. Let's map them to ZH if needed.
            val label = if (isChinese) {
                when(interest.id) {
                    "temples" -> stringResource(R.string.discover_category_temples_zh)
                    "old_streets" -> stringResource(R.string.discover_category_old_streets_zh)
                    "architecture" -> stringResource(R.string.discover_category_architecture_zh)
                    "trade" -> stringResource(R.string.discover_category_trade_zh)
                    "colonial" -> stringResource(R.string.discover_category_colonial_zh)
                    "crafts" -> stringResource(R.string.discover_category_crafts_zh)
                    else -> stringResource(interest.nameResourceId)
                }
            } else {
                stringResource(interest.nameResourceId)
            }
            
            InterestOptionButton(
                label = label,
                isSelected = interest.id in selectedInterests,
                onClick = {
                    val newState = interest.id !in selectedInterests
                    onInterestToggle(interest.id, newState)
                }
            )
        }
    }
}

@Composable
private fun InterestOptionButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(DesignTokens.cornerRadiusLarge)
    Box(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
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
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = inter
            )
        )
    }
}

@Composable
private fun SkipButton(
    isChinese: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = stringResource(if (isChinese) R.string.skip_button_zh else R.string.skip_button),
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = inter,
                color = Color(0xFFA8A8A8)
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

@Composable
private fun StartExploringButton(
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
                text = stringResource(if (isChinese) R.string.start_exploring_button_zh else R.string.start_exploring_button),
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

@Preview(
    name = "Medium Phone API 36.1",
    showBackground = true,
    device = "spec:width=411dp,height=914dp"
)
@Preview(showBackground = true)
@Composable
fun CultureInterestScreenPreview() {
    AppTheme {
        CultureInterestScreen()
    }
}
