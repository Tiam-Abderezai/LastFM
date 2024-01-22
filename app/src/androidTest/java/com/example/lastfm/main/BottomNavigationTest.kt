package com.example.lastfm.main

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.hasTestTag
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lastfm.MainScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BottomNavigationTest {

//    @get:Rule
//    val composeTestRule = createComposeRule()
    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testBottomNavigation_NavigateToDashboard() {
        composeTestRule.setContent {
//            val navController = rememberNavController()
            MainScreen()
        }
        composeTestRule
            .onNodeWithContentDescription("ArtistsScreen")
            .performClick()
        composeTestRule
            .onNode(hasTestTag("Album Screen"), useUnmergedTree = true)
            .assertIsDisplayed()
    }
}