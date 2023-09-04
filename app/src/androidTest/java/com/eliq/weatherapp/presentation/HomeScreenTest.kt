package com.eliq.weatherapp.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@SmallTest
@HiltAndroidTest
class HomeScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testHomeScreen() {
        rule.setContent {
            HomeScreenUI(
                uiModel = MockData.getMockUIModel(),
                predictions = listOf(),
                onDoneActionClick = {},
                onSuggestionItemClick = {}
            )
        }

        Thread.sleep(2000)
    }
}