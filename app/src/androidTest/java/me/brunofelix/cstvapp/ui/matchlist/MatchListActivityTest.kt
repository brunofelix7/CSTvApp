package me.brunofelix.cstvapp.ui.matchlist

import android.content.Context
import android.content.SharedPreferences
import android.widget.TextView
import androidx.preference.PreferenceManager
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.brunofelix.cstvapp.R
import me.brunofelix.cstvapp.extensions.changeAppTheme
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class MatchListActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var context: Context
    private lateinit var prefs: SharedPreferences

    @Before
    fun setUp() {
        hiltRule.inject()
        ActivityScenario.launch(MatchListActivity::class.java)

        context = InstrumentationRegistry.getInstrumentation().targetContext
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.root_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isToolbarIsDisplayedAndVisible() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun test_isToolbarTitleIsDisplayed() {
        onView(
            allOf(
                instanceOf(TextView::class.java),
                withParent(withId(R.id.toolbar))
            )
        ).check(matches(withText(R.string.title_matches)))
    }

    @Test
    fun test_isToolbarButtonsAreDisplayed() {
        onView(withId(R.id.action_refresh)).check(matches(isDisplayed()))
        onView(withId(R.id.action_daynight)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isRefreshButtonIsClicked() {
        onView(withId(R.id.action_refresh)).perform(click())
        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.match_list)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }

    @Test
    fun test_isSheetsDialogIsDisplayed() {
        onView(withId(R.id.action_daynight)).perform(click())
        onView(withId(R.id.layout_sheets_dialog)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isDarkModeIsOn() {
        prefs.edit().apply {
            putBoolean(context.getString(R.string.theme_key), true)
            apply()
        }
        onView(withId(R.id.action_daynight)).perform(click())
        onView(withId(R.id.layout_sheets_dialog)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).check(matches(isChecked()))
    }

    @Test
    fun test_isDarkModeIsOff() {
        prefs.edit().apply {
            putBoolean(context.getString(R.string.theme_key), false)
            apply()
        }
        onView(withId(R.id.action_daynight)).perform(click())
        onView(withId(R.id.layout_sheets_dialog)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).check(matches(isNotChecked()))
    }
}
