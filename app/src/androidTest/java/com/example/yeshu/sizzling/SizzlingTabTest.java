package com.example.yeshu.sizzling;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SizzlingTabTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void homeActivityTest() {
        ViewInteraction cardView = onView(
                allOf(withId(R.id.cardView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.HomeActivityRecycleview),
                                        0),
                                0),
                        isDisplayed()));
        cardView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.ingredients_steps), withText("Ingredients List"),
                        childAtPosition(
                                allOf(withId(R.id.second_Activity_cardView),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.item_list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));


        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.item_description),
                        childAtPosition(
                                withId(R.id.item_detail),
                                2),
                        isDisplayed()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.receipeName), withText("Yellow Cake"),
                        childAtPosition(
                                allOf(withId(R.id.cardView),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                1),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction cardView2 = onView(
                allOf(withId(R.id.cardView),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.HomeActivityRecycleview),
                                        2),
                                0),
                        isDisplayed()));
        cardView2.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.ingredients_steps), withText("Ingredients List"),
                        childAtPosition(
                                allOf(withId(R.id.second_Activity_cardView),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                                0)),
                                0),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.item_list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)));


        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.item_description),
                        childAtPosition(
                                withId(R.id.item_detail),
                                2),
                        isDisplayed()));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
