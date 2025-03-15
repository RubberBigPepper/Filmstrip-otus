package com.akmanaev.filmstrip

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Optional
import javax.inject.Inject


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserFlowTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var countingIdlingResource: Optional<CountingIdlingResource>

    @Before
    fun before() {
        activityRule.scenario.onActivity {
          //  ((it.application as FilmApplication).getAppComponent() as TestAppComponent).inject(this)
        }
//        IdlingRegistry.getInstance().register(countingIdlingResource.get())
    }

    private fun withIndex(matcher: Matcher<View?>, index: Int): Matcher<View?> {
        return object : TypeSafeMatcher<View?>() {
            var currentIndex: Int = 0

            override fun describeTo(description: Description) {
                description.appendText("with index: ")
                description.appendValue(index)
                matcher.describeTo(description)
            }

            override fun matchesSafely(view: View?): Boolean {
                return matcher.matches(view) && currentIndex++ == index
            }
        }
    }

    private fun ViewInteraction.isVisible() = getViewAssertion(ViewMatchers.Visibility.VISIBLE)

    private fun ViewInteraction.isInvisible() = getViewAssertion(ViewMatchers.Visibility.INVISIBLE)

    private fun getViewAssertion(visibility: ViewMatchers.Visibility): Boolean {
        return ViewMatchers.withEffectiveVisibility(visibility).matches(visibility)
    }

    @Test
    fun simpleFlowFilmFrames() {
        while (onView(withId(R.id.loadingAnim)).isVisible()) {
            runBlocking { delay(1000) }
        }

        runBlocking { delay(10000) }

        onView(withIndex(withId(R.id.down_iv), 0))
            .perform(click())

        onView(withId(R.id.expandableRecyclerView))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, click()
                )
            )


        runBlocking {  delay(10000) }
    }

    @Test
    fun simpleFlowFilmDetails() {
        while (onView(withId(R.id.loadingAnim)).isVisible()) {
            runBlocking { delay(1000) }
        }

        runBlocking { delay(10000) }

        onView(withIndex(withId(R.id.down_iv), 0))
            .perform(click())

        onView(withId(R.id.expandableRecyclerView))
            .perform(
                actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    1, longClick()
                )
            )


        runBlocking {  delay(10000) }
    }

}