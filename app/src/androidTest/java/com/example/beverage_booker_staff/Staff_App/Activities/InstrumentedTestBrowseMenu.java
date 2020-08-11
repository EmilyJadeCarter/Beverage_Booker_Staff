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
     * test are a staff member exists with the id 0001 and an entry in the items
     * database exists with a name of "banana", description of "its a banana"
     * and a price of 12.5.
     */
    @Test
    public void ClickOnMenu_AssertMenuOpens() {
        onView(withId(R.id.MenuButton))
                .perform(click());
        onView(withId(R.id.MenuButton))
                .check(doesNotExist());
        onView(withId(R.id.OrdersButton))
                .check(doesNotExist());
        onView(withId(R.id.DeliveriesButton))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.itemID), hasSibling(withText("1"))))
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
        onView(withId(R.id.addMenuItem))
                .check(matches(isDisplayed()));
    }
}