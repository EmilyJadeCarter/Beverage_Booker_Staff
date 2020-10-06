package com.example.beverage_booker_staff.Staff_App.Activities;

import androidx.test.espresso.action.ViewActions;
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
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class IntegrationTestManageStaff {
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
        onView(withId(R.id.manageStaffButton))
                .perform(click());
        Thread.sleep(4000);

        //ManageStaffActivity
        onView(withId(R.id.manageStaffButton))
                .check(doesNotExist());
        onView(withId(R.id.addStaffButton))
                .check(matches(isDisplayed()));
        onView(withId(R.id.addStaffButton))
                .perform(click());
        Thread.sleep(4000);

        //CreateStaffActivity
        onView(allOf(ViewMatchers.withId(R.id.editStaffLevel), hasSibling(withHint("Staff level"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.editStaffLevel), hasSibling(withHint("Staff level"))))
                .perform(typeText("2"));
        onView(allOf(ViewMatchers.withId(R.id.editTextFirstName), hasSibling(withHint("First name"))))
                .perform(typeText("Derek"));
        onView(allOf(ViewMatchers.withId(R.id.editTextLastName), hasSibling(withHint("Last name"))))
                .perform(typeText("Organ"));
        onView(allOf(ViewMatchers.withId(R.id.editTextLastName), hasSibling((withText("Organ")))))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonRegister))
                .perform(click());
        Thread.sleep(4000);

        //ManageStaffActivity
        onView(allOf(ViewMatchers.withId(R.id.deleteStaffMember), hasSibling(withText("Derek"))))
                .check(matches(isDisplayed()));
        onView(allOf(ViewMatchers.withId(R.id.deleteStaffMember), hasSibling(withText("Derek"))))
                .perform(click());
        Thread.sleep(4000);
        onView(allOf(ViewMatchers.withId(R.id.deleteStaffMember), hasSibling(withText("Derek"))))
                .check(doesNotExist());
    }
}
