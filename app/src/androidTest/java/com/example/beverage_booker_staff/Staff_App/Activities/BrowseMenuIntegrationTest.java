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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BrowseMenuIntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This tests conducts a run through of the program interacting with
     * all applicable activities tied to BrowseMenu, the prerequisites for this
     * test are a staff member exists with the id 0001 and an entry in the items
     * database exists with a name of "banana", description of "its a banana"
     * and a price of 12.5.
     */
    @Test
    public void BrowseMenuIntegrationTest() {
        //MainActivity
        onView(withId(R.id.editTextStaffID))
                .perform(typeText("0001"));
        onView(withId(R.id.button_ValidateId))
                .perform(click());

        //MainMenuActivity
        onView(withId(R.id.button_ValidateId))
                .check(doesNotExist());
        onView(withId(R.id.MenuButton))
                .perform(click());

        //BrowseMenuActivity
        onView(withId(R.id.MenuButton))
                .check(doesNotExist());
        onView(withId(R.id.addMenuItem))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemDesc), hasSibling(withText("its a banana"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemPrice), hasSibling(withText("$12.50"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.modifyMenuItem), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("banana"))))
                .check(matches(isDisplayed()));
    }
}