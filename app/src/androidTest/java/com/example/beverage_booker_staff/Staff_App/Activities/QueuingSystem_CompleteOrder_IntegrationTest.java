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
public class QueuingSystem_CompleteOrder_IntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    /**
     * This test is a run through of the program to make sure the integration
     * of the queuing system is implemented correctly with links working and components
     * displaying correctly.
     * Requires an order with id 25 and a drink item of Drink1 but can be changed in the script
     * for different circumstances.
     */
    @Test
    public void QueuingSystemIntegrationTest() {
        //MainActivity
        onView(withId(R.id.editTextStaffID))
                .perform(typeText("1001"));
        onView(withId(R.id.button_ValidateId))
                .perform(click());

        //MainMenuActivity
        onView(withId(R.id.button_ValidateId))
                .check(doesNotExist());
        onView(withId(R.id.OrdersButton))
                .perform(click());

        //ViewOrderActivity
        onView(withId(R.id.OrdersButton))
                .check(doesNotExist());
        onView(withId(R.id.textViewStaffTitle))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.buttonStartOrder), hasSibling(withText("25"))))
                .perform(click());

        //OrderIndividualItem
        onView(allOf(ViewMatchers.withId(R.id.textView_itemTitle), hasSibling(withText("Drink1"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.checkBox_complete), hasSibling(withText("Drink1"))))
                .perform(click());
        onView(allOf(ViewMatchers.withId(R.id.button_Complete), hasSibling(withText("Complete Order"))))
                .perform(click());

        //OrderIndividualItem Confirmation
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Cancel"))
                .perform(click());

        //OrderIndividualItem Again
        onView(allOf(ViewMatchers.withId(R.id.button_Complete), hasSibling(withText("Complete Order"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.button_Complete), hasSibling(withText("Complete Order"))))
                .perform(click());

        //OrderIndividualItem Confirmation
        onView(withText("Confirmation"))
                .check(matches(isDisplayed()));
        onView(withText("Confirm"))
                .perform(click());

        //ViewOrderActivity
        onView(withId(R.id.button_Complete))
                .check(doesNotExist());
        onView(withId(R.id.textViewStaffTitle))
                .check(matches(isDisplayed()));
    }
}
