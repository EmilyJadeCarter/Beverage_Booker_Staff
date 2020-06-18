package com.example.beverage_booker_staff.Staff_App.Activities;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class InstrumentedViewActiveOrderTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * These tests run in conjuntion with the Fill Cart and Place Order tests from the Customer App.
     * The tests are using data from an order placed by these previous instrumented tests,
     * and can be run in order as an integration test.
     */

    /**
     * Checks that all layout fields are correctly displayed
     * Also checks that an empty staffID or staffID of incorrect length is not logged in.
     */
    @Test
    public void StaffLogin_InvalidData() {

        onView(withId(R.id.editTextStaffID))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_ValidateId))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.editTextStaffID))
                .check(matches(hasErrorText("ID is required")));
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("000"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.editTextStaffID))
                .check(matches(hasErrorText("ID must be 4 digits")));
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("00045"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.editTextStaffID))
                .check(matches(hasErrorText("ID must be 4 digits")));
    }


    /**
     * Checks that a valid staffID is logged in correctly.
     */
    @Test
    public void StaffLogin_SuccessfulLogin() {
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
    }


    /**
     * Checks the fields for the View Active Order Activity are correctly displayed.
     */
    @Test
    public void ViewActiveOrder_CheckFields() {
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.textViewOrderIDTitle))
                .check(matches(isDisplayed()));
        onView(withId(R.id.textViewStaffTitle))
                .check(matches(isDisplayed()));
        onView(withId(R.id.buttonStartOrder))
                .check(matches(isDisplayed()));
    }


    /**
     * Checks the fields for the View Cart Items Activity are correctly displayed.
     * Also checks that the order/cart items match what is meant to be in that order.
     */
    @Test
    public void ViewCartItems_CheckFields_CheckItems() {
        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("0001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        onView(withId(R.id.buttonStartOrder))
                .perform(click());
        onView(withId(R.id.textViewOrderNumberTitle))
                .check(matches(isDisplayed()));
        onView(withId(R.id.button_Complete))
                .check(matches(isDisplayed()));
        onView(withId(R.id.textView_itemTitle))
                .check(matches(withText("banana")));
        onView(withId(R.id.itemQuantityHeading))
                .check(matches(isDisplayed()));
        onView(withId(R.id.itemQuantityValue))
                .check(matches(isDisplayed()));
        onView(withId(R.id.checkBox_complete))
                .check(matches(isDisplayed()));
    }
}



