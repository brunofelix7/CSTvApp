package me.brunofelix.cstvapp.ui.matchlist

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.brunofelix.cstvapp.R
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

    @Before
    fun setUp() {
        hiltRule.inject()
        ActivityScenario.launch(MatchListActivity::class.java)
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
}
