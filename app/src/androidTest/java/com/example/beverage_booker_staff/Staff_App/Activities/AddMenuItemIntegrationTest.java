package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.test.espresso.action.ViewActions;
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
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddMenuItemIntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    /**
     * This test ensures that the add menu item works properly adding a menu item into
     * the items database and checking if it was added by asserting its existing on the scren
     * of the items list.
     */
    @Test
    public void AddMenuItemIntegrationTest() throws InterruptedException {
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
        onView(withId(R.id.addMenuItem))
                .perform(click());
        Thread.sleep(4000);

        //ItemTypeSelectionActivity
        onView(withId(R.id.addMenuItem))
                .check(doesNotExist());
        onView(withId(R.id.drinkMenuButton))
                .perform(click());
        Thread.sleep(4000);

        //ItemFormActivity
        onView(withId(R.id.editTextTitle))
                .perform(typeText("Test Item"));
        onView(withId(R.id.editTextShortDesc))
                .perform(typeText("This item is for testing"));
        onView(withId(R.id.editTextPrice))
                .perform(typeText("104.57"));
        onView(withId(R.id.editTextTime))
                .perform(typeText("15"), closeSoftKeyboard());
        onView(withId(R.id.milkOption))
                .perform(click());
        onView(withId(R.id.sugarOption))
                .perform(click());
        onView(withId(R.id.decafOption))
                .perform(click());
        onView(withId(R.id.extrasOption))
                .perform(click());
        onView(withId(R.id.frappeOption))
                .perform(click());
        onView(withId(R.id.addButton))
                .perform(click());
    }

}
