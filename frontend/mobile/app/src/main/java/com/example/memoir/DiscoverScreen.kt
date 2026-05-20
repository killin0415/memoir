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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.memoir.ui.components.UntitledIcon
import com.example.memoir.ui.icons.*
import com.example.memoir.ui.theme.AppTheme
import com.example.memoir.ui.theme.inter
import com.example.memoir.ui.theme.judson

/**
 * Discover screen displaying cultural routes and categories.
 * Filters content based on [initialInterests]. If empty (skipped), shows everything.
 */
@Composable
fun DiscoverScreen(
    selectedLanguage: String = "en",
    initialInterests: Set<String> = emptySet(),
    modifier: Modifier = Modifier
) {
    val isChinese = selectedLanguage == "zh"

    // Master order of interest IDs to ensure consistency across screens
    val masterInterestOrder = listOf("all", "temples", "old_streets", "architecture", "trade", "colonial", "crafts")

    val categoryMapEn = linkedMapOf(
        "all" to stringResource(R.string.discover_category_all),
        "temples" to stringResource(R.string.culture_temples),
        "old_streets" to stringResource(R.string.culture_old_streets),
        "architecture" to stringResource(R.string.culture_architecture),
        "trade" to stringResource(R.string.culture_trade),
        "colonial" to stringResource(R.string.culture_colonial),
        "crafts" to stringResource(R.string.culture_crafts)
    )

    val categoryMapZh = linkedMapOf(
        "all" to stringResource(R.string.discover_category_all_zh),
        "temples" to stringResource(R.string.discover_category_temples_zh),
        "old_streets" to stringResource(R.string.discover_category_old_streets_zh),
        "architecture" to stringResource(R.string.discover_category_architecture_zh),
        "trade" to stringResource(R.string.discover_category_trade_zh),
        "colonial" to stringResource(R.string.discover_category_colonial_zh),
        "crafts" to stringResource(R.string.discover_category_crafts_zh)
    )

    val categories = if (isChinese) categoryMapZh.values.toList() else categoryMapEn.values.toList()
    
    // Default to 'All' if interests are empty (Skip clicked).
    // Otherwise, pick the interest that appears FIRST in the masterInterestOrder.
    var selectedCategory by remember { 
        mutableStateOf(if (initialInterests.isEmpty()) categories[0] else {
            val firstIdInDefaultOrder = masterInterestOrder.drop(1).find { it in initialInterests } ?: "all"
            if (isChinese) categoryMapZh[firstIdInDefaultOrder] ?: categories[0] else categoryMapEn[firstIdInDefaultOrder] ?: categories[0]
        }) 
    }

    val routes = listOf(
        RouteData(
            id = "temples",
            titleEn = "Sounds of Temple Tainan",
            titleZh = stringResource(R.string.route_sounds_of_temple_zh),
            categoryEn = stringResource(R.string.culture_temples),
            categoryZh = stringResource(R.string.discover_category_temples_zh),
            imageRes = R.drawable.sounds_of_temple
        ),
        RouteData(
            id = "architecture",
            titleEn = "Layers of Colonial Architecture",
            titleZh = stringResource(R.string.route_layers_of_colonial_architecture_zh),
            categoryEn = stringResource(R.string.culture_architecture),
            categoryZh = stringResource(R.string.discover_category_architecture_zh),
            imageRes = R.drawable.sea_protection
        ),
        RouteData(
            id = "architecture",
            titleEn = "Brick, Arches, and Time",
            titleZh = stringResource(R.string.route_brick_arches_and_time_zh),
            categoryEn = stringResource(R.string.culture_architecture),
            categoryZh = stringResource(R.string.discover_category_architecture_zh),
            imageRes = R.drawable.faith_hidden
        ),
        RouteData(
            id = "temples",
            titleEn = "Sea Protection to City Beliefs",
            titleZh = stringResource(R.string.route_sea_protection_zh),
            categoryEn = stringResource(R.string.culture_temples),
            categoryZh = stringResource(R.string.discover_category_temples_zh),
            imageRes = R.drawable.sea_protection
        ),
        RouteData(
            id = "temples",
            titleEn = "Faith Hidden in Alleyways",
            titleZh = stringResource(R.string.route_faith_hidden_zh),
            categoryEn = stringResource(R.string.culture_temples),
            categoryZh = stringResource(R.string.discover_category_temples_zh),
            imageRes = R.drawable.faith_hidden
        )
    )

    // Filtering logic: 
    // If selectedCategory is "All", show all.
    // Otherwise, filter by category label match.
    val filteredRoutes = if (selectedCategory == categories[0]) {
        routes
    } else {
        routes.filter { 
            val catLabel = if (isChinese) it.categoryZh else it.categoryEn
            catLabel == selectedCategory
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DesignTokens.colorLanguageSelectionBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Section
            Column(
                modifier = Modifier
                    .padding(horizontal = 29.dp)
                    .padding(top = 60.dp)
            ) {
                Text(
                    text = if (isChinese) stringResource(R.string.discover_subtitle_zh) else stringResource(R.string.discover_subtitle),
                    style = TextStyle(
                        fontFamily = inter,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = if (isChinese) stringResource(R.string.discover_headline_zh) else stringResource(R.string.discover_headline),
                    style = TextStyle(
                        fontFamily = judson,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(11.dp))

                // Search Bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .background(Color(0xFFD7D7D7), RoundedCornerShape(50.dp))
                        .padding(horizontal = 20.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color.Gray, RoundedCornerShape(12.dp))
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (isChinese) stringResource(R.string.discover_search_placeholder_zh) else stringResource(R.string.discover_search_placeholder),
                            style = TextStyle(
                                fontFamily = inter,
                                fontSize = 16.sp,
                                color = Color(0xFF7B7B7B)
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Categories
            LazyRow(
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    CategoryChip(
                        label = category,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Routes List
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 29.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                filteredRoutes.forEach { route ->
                    RouteCard(route, isChinese)
                }
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        BottomNavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter),
            isChinese = isChinese
        )
    }
}

data class RouteData(
    val id: String,
    val titleEn: String,
    val titleZh: String,
    val categoryEn: String,
    val categoryZh: String,
    val imageRes: Int
)

@Composable
fun CategoryChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(30.dp)
            .background(
                color = if (isSelected) DesignTokens.colorMaroon else Color.Transparent,
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = if (isSelected) 0.dp else 1.dp,
                color = if (isSelected) Color.Transparent else DesignTokens.colorBorderGray,
                shape = RoundedCornerShape(50.dp)
            )
            .clip(RoundedCornerShape(50.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontFamily = inter,
                fontSize = 12.sp,
                color = if (isSelected) Color.White else Color.Black
            )
        )
    }
}

@Composable
fun RouteCard(route: RouteData, isChinese: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFD7D7D7))
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(246.dp)
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(route.imageRes),
                contentDescription = if (isChinese) route.titleZh else route.titleEn,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (isChinese) route.titleZh else route.titleEn,
                style = TextStyle(
                    fontFamily = judson,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .border(1.dp, DesignTokens.colorBorderGray, RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (isChinese) route.categoryZh else route.categoryEn,
                        style = TextStyle(
                            fontFamily = inter,
                            fontSize = 11.sp,
                            color = Color.Black
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .background(Color.Black, RoundedCornerShape(50.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .clickable { /* Handle click */ }
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isChinese) stringResource(R.string.discover_more_zh) else stringResource(R.string.discover_more),
                            style = TextStyle(
                                fontFamily = inter,
                                fontSize = 10.sp,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Image(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = null,
                            modifier = Modifier.size(9.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier, isChinese: Boolean) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White)
            .border(1.dp, Color(0xFFC1C1C1)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(
            label = if (isChinese) stringResource(R.string.discover_nav_home_zh) else stringResource(R.string.discover_nav_home),
            icon = UntitledIcons.HomeIcon,
            isSelected = true
        )
        BottomNavItem(
            label = if (isChinese) stringResource(R.string.discover_nav_saved_zh) else stringResource(R.string.discover_nav_saved),
            icon = UntitledIcons.SavedIcon,
            isSelected = false
        )
        BottomNavItem(
            label = if (isChinese) stringResource(R.string.discover_nav_memories_zh) else stringResource(R.string.discover_nav_memories),
            icon = UntitledIcons.MemoriesIcon,
            isSelected = false
        )
    }
}

@Composable
fun BottomNavItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean
) {
    val color = if (isSelected) DesignTokens.colorMaroon else Color(0xFF5C5C5C)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { /* Handle click */ }
    ) {
        UntitledIcon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            size = 24.dp
        )
        Text(
            text = label,
            style = TextStyle(
                fontFamily = inter,
                fontSize = 10.sp,
                color = color
            )
        )
    }
}

@Preview(showBackground = true, device = "spec:width=412dp,height=915dp")
@Composable
fun DiscoverScreenPreview() {
    AppTheme {
        DiscoverScreen()
    }
}
