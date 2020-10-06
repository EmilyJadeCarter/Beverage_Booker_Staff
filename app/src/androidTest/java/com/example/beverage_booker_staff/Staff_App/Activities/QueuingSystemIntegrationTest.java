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
public class QueuingSystemIntegrationTest {
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);
    /**
     * This test is a run through of the program to make sure the integration
     * of the queuing system is implemented correctly with links working and components
     * displaying correctly.
     */
    @Test
    public void QueuingSystemIntegrationTest() throws InterruptedException {
        //MainActivity
        onView(withId(R.id.editTextStaffID))
                .perform(typeText("1001"));
        onView(withId(R.id.button_ValidateId))
                .perform(click());
        Thread.sleep(4000);

        //MainMenuActivity
        onView(withId(R.id.button_ValidateId))
                .check(doesNotExist());
        onView(withId(R.id.OrdersButton))
                .perform(click());
        Thread.sleep(4000);

        //ViewOrderActivity
        onView(withId(R.id.OrdersButton))
                .check(doesNotExist());
        onView(withId(R.id.textViewStaffTitle))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.buttonStartOrder), hasSibling(withText("128"))))
                .perform(click());
        Thread.sleep(4000);

        //OrderIndividualItem
        onView(allOf(ViewMatchers.withId(R.id.cartItemName), hasSibling(withText("Flat White"))))
                .check(matches(isDisplayed()));
    }
}
