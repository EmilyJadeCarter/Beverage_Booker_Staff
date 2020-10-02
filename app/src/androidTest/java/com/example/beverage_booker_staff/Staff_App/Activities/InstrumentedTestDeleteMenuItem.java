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
public class InstrumentedTestDeleteMenuItem {
    @Rule
    public ActivityScenarioRule<MainMenuActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainMenuActivity.class);

    /**
     * This test ensures that the popup menu appears when the delete button for
     * banana is clicked. The prerequisites for this test is that an item named
     * banana exists.
     */
    @Test
    public void DeleteItem_PopupAppears() throws InterruptedException {
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("Test Item"))))
                .perform(click());
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
    }

    /**
     * This test ensures that the cancel button on popup closes the popup
     * and banana is not deleted from the items list. The prerequisites
     * for this test is that an item named banana exists.
     */
    @Test
    public void DeleteItem_PopupAppears_Cancel() throws InterruptedException {
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("Test Item"))))
                .perform(click());
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Cancel"))
                .perform(click());
        onView(withText("Confirmation"))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("Test Item"))))
                .check(matches(isDisplayed()));
    }

    /**
     * From main menu this test click menu item and from there
     * deletes a test item, this test item will have the following details
     * a name of "test", description of "desc" price of 10.50 and image called "img"
     * the following sql command can be used to add this item to the database:
     *INSERT INTO `items`(`id`, `title`, `shortdesc`, `price`, `image`) VALUES (0,"test","desc",10.50,"img")
     */
    @Test
    public void DeleteItem_PopupAppears_Confirm() throws InterruptedException {
        onView(withId(R.id.MenuButton))
                .perform(click());
        Thread.sleep(4000);
        onView(allOf(ViewMatchers.withId(R.id.deleteMenuItem), hasSibling(withText("Test Item"))))
                .perform(click());
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Confirm"))
                .perform(click());
        Thread.sleep(4000);
        onView(withText("Confirmation"))
                .check(doesNotExist());
        onView(allOf(ViewMatchers.withId(R.id.itemName), hasSibling(withText("Test Item"))))
                .check(doesNotExist());
    }
}