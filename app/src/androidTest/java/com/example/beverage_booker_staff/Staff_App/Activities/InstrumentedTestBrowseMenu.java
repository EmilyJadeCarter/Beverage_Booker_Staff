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
public class InstrumentedTestBrowseMenu {
    @Rule
    public ActivityScenarioRule<MainMenuActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainMenuActivity.class);

    /**
     * From main menu this test click menu button and ensures the screen changed
     * by checking and making sure every category of item is displayed properly
     * in which it checks all components related to banana. he prerequisites for this
     * test are a staff member exists with the id 1001.
     */
    @Test
    public void ClickOnMenu_AssertMenuOpens() throws InterruptedException {
        //MainMenuActivity
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);

        //BrowseMenuActivity
        onView(withId(R.id.MenuButton))
                .check(doesNotExist());
        onView(withId(R.id.addMenuItem))
                .check(matches(isDisplayed()));
    }

    @Test
    public void ClickOnMenu_AssertItemsExist() throws InterruptedException {
        //MainMenuActivity
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);

        //BrowseMenuActivity
        onView(withId(R.id.MenuButton))
                .check(doesNotExist());
        onView(withId(R.id.addMenuItem))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemID), hasSibling(withText("7"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("Flat White"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.itemPrice), hasSibling(withText("Flat White"))))
                .check(matches(hasSibling(withText("$3.90"))));
        onView(allOf(ViewMatchers.withId(R.id.modifyMenuItem), hasSibling(withText("Flat White"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("Flat White"))))
                .check(matches(isDisplayed()));
    }
}