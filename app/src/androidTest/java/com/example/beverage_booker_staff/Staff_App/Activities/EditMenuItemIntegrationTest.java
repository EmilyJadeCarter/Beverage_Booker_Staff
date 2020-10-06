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
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditMenuItemIntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    /**
     * This test ensures that the edit menu does what it is supposed to
     */
    @Test
    public void EditMenuItemIntegrationTest() throws InterruptedException {
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
        onView(allOf(ViewMatchers.withId(R.id.modifyMenuItem), hasSibling(withText("Flat White"))))
                .perform(click());
        Thread.sleep(4000);

        //ItemFormActivity
        onView(withId(R.id.editTextTitle))
                .check(matches((withText("Flat White"))));
        onView(withId(R.id.editTextShortDesc))
                .check(matches((withText(""))));
        onView(withId(R.id.editTextPrice))
                .check(matches((withText("3.9"))));
        onView(withId(R.id.editTextTime))
                .check(matches((withText("2"))));
        onView(withId(R.id.milkOption))
                .check(matches(isChecked()));
        onView(withId(R.id.sugarOption))
                .check(matches(isChecked()));
        onView(withId(R.id.decafOption))
                .check(matches(isChecked()));
        onView(withId(R.id.extrasOption))
                .check(matches(isChecked()));
        onView(withId(R.id.frappeOption))
                .check(matches(isNotChecked()));

        onView(withId(R.id.editTextShortDesc))
                .perform(typeText("Test"), closeSoftKeyboard());
        onView(withId(R.id.addButton))
                .perform(click());
        Thread.sleep(4000);

        //BrowseMenuActivity
        onView(allOf(ViewMatchers.withId(R.id.itemDesc), hasSibling(withText("Test"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.modifyMenuItem), hasSibling(withText("Flat White"))))
                .perform(click());
        Thread.sleep(4000);

        //ItemFormActivity
        onView(withId(R.id.editTextShortDesc))
                .perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.addButton))
                .perform(click());
        Thread.sleep(4000);

    }

}
