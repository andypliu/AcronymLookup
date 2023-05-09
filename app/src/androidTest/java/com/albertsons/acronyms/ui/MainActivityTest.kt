package com.albertsons.acronyms.ui

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.albertsons.acronyms.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val textView = onView(
            allOf(
                withText("Acronym Lookup"),
                withParent(
                    allOf(
                        withId(androidx.appcompat.R.id.action_bar),
                        withParent(withId(androidx.appcompat.R.id.action_bar_container))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("Acronym Lookup")))

        val editText = onView(
            allOf(
                withId(R.id.acronym), withText(""),
                withParent(withParent(withId(R.id.my_nav_host_fragment))),
                isDisplayed()
            )
        )
        editText.check(matches(withText("")))

        val button = onView(
            allOf(
                withId(R.id.lookup), withText("LOOKUP"),
                withParent(withParent(withId(R.id.my_nav_host_fragment))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))

        val appCompatEditText = onView(
            allOf(
                withId(R.id.acronym),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.my_nav_host_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText.perform(click())

        val appCompatEditText2 = onView(
            allOf(
                withId(R.id.acronym),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.my_nav_host_fragment),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        appCompatEditText2.perform(replaceText("HTML"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.lookup), withText("Lookup"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.my_nav_host_fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        Thread.sleep(3000)  // for simplicity, could use Idling Resources synchronize UI testing

        val textView2 = onView(
            allOf(
                withId(R.id.definitionTextView), withText("hypertext markup language"),
                withParent(withParent(withId(R.id.definitionRecyclerView))),
                isDisplayed()
            )
        )
        textView2.check(matches(withText("hypertext markup language")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
