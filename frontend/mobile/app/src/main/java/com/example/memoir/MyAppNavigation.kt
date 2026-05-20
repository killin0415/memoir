package com.example.memoir

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import androidx.activity.compose.BackHandler

@Composable
fun MyAppNavigation() {
    val backStack = remember { mutableStateListOf<Any>(WelcomeDestination) }
    var selectedLanguage by remember { mutableStateOf("en") }
    var userInterests by remember { mutableStateOf(setOf<String>()) }

    BackHandler(enabled = backStack.size > 1) {
        backStack.removeLastOrNull()
    }
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        transitionSpec = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(400)
            ) togetherWith ExitTransition.None
        },
        popTransitionSpec = {
            EnterTransition.None
                .togetherWith (
                    slideOutHorizontally(
                        targetOffsetX = { fullWidth -> fullWidth },
                        animationSpec = tween(400)
                    )
                )
        },
        entryProvider = { key ->
            when (key) {
                is WelcomeDestination -> NavEntry(key) {
                    WelcomeScreen(
                        selectedLanguage = selectedLanguage,
                        onBeginTourClick = {
                            backStack.add(LanguageSelectionDestination)
                        }
                    )
                }
                is LanguageSelectionDestination -> NavEntry(key) {
                    LanguageSelectionScreen(
                        initialLanguage = selectedLanguage,
                        onLanguageSelect = { languageCode ->
                            selectedLanguage = languageCode
                        },
                        onNextClick = {
                            backStack.add(CultureInterestDestination)
                        }
                    )
                }
                is CultureInterestDestination -> NavEntry(key) {
                    CultureInterestScreen(
                        selectedLanguage = selectedLanguage,
                        initialInterests = userInterests,
                        onInterestSelect = { interestId, isSelected ->
                            // Update interests as user toggles them
                            userInterests = if (isSelected) {
                                userInterests + interestId
                            } else {
                                userInterests - interestId
                            }
                        },
                        onStartExploringClick = {
                            // Interests are already set via onInterestSelect
                            backStack.add(DiscoverDestination)
                        },
                        onSkipClick = {
                            userInterests = emptySet() // Reset to empty for "all information"
                            backStack.add(DiscoverDestination)
                        }
                    )
                }
                is DiscoverDestination -> NavEntry(key) {
                    DiscoverScreen(
                        selectedLanguage = selectedLanguage,
                        initialInterests = userInterests
                    )
                }
                else -> NavEntry(Unit) { Text("Unknown destination") }
            }
        }
    )

}
