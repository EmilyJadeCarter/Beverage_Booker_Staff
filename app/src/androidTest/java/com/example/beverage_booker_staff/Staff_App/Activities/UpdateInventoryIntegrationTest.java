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
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasSibling;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class UpdateInventoryIntegrationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This instrumented integration test starts by logging a registered staff member into the app.
     * It then proceeds to the Inventory page.
     * Here it checks for the menu item 'Blueberry Muffin'. It also checks that its displayed stock
     * level is '30'.
     * Following the match, it clears the value from the EditText stock field.
     * The test the enters a new stock value of '50'.
     * Finally, it clicks the 'Update' button for this item, and confirms that the new stock value
     * matches the update input.
     */
    @Test
    public void updateInventoryIntegrationTest() throws InterruptedException {

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
        Thread.sleep(1000);

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemStock), hasSibling(withText("30"))))
                .perform(clearText());
        Thread.sleep(1000);

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemStock), hasSibling(withText(""))))
                .perform(typeText("50"));
        Thread.sleep(1000);

        onView(allOf(ViewMatchers.withId(R.id.updateInventoryItem), hasSibling(withText("50"))))
                .perform(click());
        Thread.sleep(1000);

        onView(allOf(ViewMatchers.withId(R.id.inventoryItemStock), hasSibling(withText("50"))))
                .check(matches(isDisplayed()));
        Thread.sleep(1000);




    }
}
