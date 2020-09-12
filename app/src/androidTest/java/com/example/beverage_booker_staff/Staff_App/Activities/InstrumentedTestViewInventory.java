package com.example.beverage_booker_staff.Staff_App.Activities;


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
public class InstrumentedTestViewInventory {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Checks that the Inventory Button in the Main Menu is displayed
     */
    @Test
    public void isInventoryButtonDisplayed() {

        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("1001"), closeSoftKeyboard());

        onView(withId(R.id.button_ValidateId))
                .perform(click());

        onView(withId(R.id.inventoryButton))
                .check(matches(isDisplayed()));
    }


    /**
     * Checks that the Inventory Button in the Main Menu is working
     */
    @Test
    public void isInventoryButtonFunctioning() {

        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("1001"), closeSoftKeyboard());

        onView(withId(R.id.button_ValidateId))
                .perform(click());

        onView(withId(R.id.inventoryButton))
                .perform(click());
    }


    /**
     * Checks that the Inventory fields are displaying correctly for a menu item
     */
    @Test
    public void isFieldsForInventoryDisplayed() throws InterruptedException {

        onView(withId(R.id.editTextStaffID))
                .perform(replaceText("1001"), closeSoftKeyboard());
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.inventoryButton))
                .perform(click());
        Thread.sleep(1000);

        onView(withId(R.id.recyclerViewInventory))
                .check(matches(isDisplayed()));

        onView(withId(R.id.textViewInventoryTitle))
                .check(matches(isDisplayed()));

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemID), hasSibling(withText("9"))))
                .check(matches(isDisplayed()));

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemName), hasSibling(withText("Blueberry Muffin"))))
                .check(matches(isDisplayed()));

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemStock), hasSibling(withText("30"))))
                .check(matches(isDisplayed()));

    }

}
