package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import com.example.beverage_booker_staff.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedTestDeliveries {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);




    /**
     * Checks the fields for the Main Menu Activity are correctly displayed.
     */
    @Test
    public void isButtonsForMainMenuDisplayed() {
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.OrdersButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.DeliveriesButton))
                .check(matches(isDisplayed()));
    }


    /**
     * Checks the deliveries button is functioning correctly.
     */
    @Test
    public void isDeliveriesButtonFunctioning() {
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.DeliveriesButton))
                .perform(click());
    }


    /**
     * Checks that the fields for the delivery are displayed correctly.
     */
    @Test
    public void isFieldsForDeliveriesActivityDisplayed() {

        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.DeliveriesButton))
                .perform(click());
        onView(withId(R.id.relativeLayout))
                .check(matches(isDisplayed()));
        Espresso.onView(withId(R.id.recyclerView3)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(allOf(ViewMatchers.withId(R.id.recyclerView), hasSibling(withText("123"))))
                .perform(click());

    }
}
