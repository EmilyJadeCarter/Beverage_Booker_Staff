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
public class DeleteMenuItemIntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    /**
     * This test ensures that the popup menu appears when the delete button for
     * banana is clicked. The program then clicks cancel The prerequisites for
     * this test is that an item named test exists.
     */
    @Test
    public void DeleteMenuItemIntegrationTest() throws InterruptedException {
        //MainActivity
        onView(withId(R.id.editTextStaffID))
                .perform(typeText("1001"));
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        Thread.sleep(4000);

        //MainMenuActivity
        onView(withId(R.id.button_ValidateId))
                .check(doesNotExist());
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);

        //BrowseMenuActivity
        onView(withId(R.id.MenuButton))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("test"))))
                .perform(click());

        //BrowseMenuActivity with delete menu item popup
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Cancel"))
                .perform(click());

        //BrowseMenuActivity
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("test"))))
                .check(matches(isDisplayed()));
        onView(withText("Confirmation"))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("test"))))
                .perform(click());

        //BrowseMenuActivity with delete menu item popup
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Confirm"))
                .perform(click());

        //BrowseMenuActivity
        onView(withText("Confirmation"))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("test"))))
                .check(doesNotExist());

    }
}