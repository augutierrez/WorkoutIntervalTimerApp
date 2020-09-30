package com.example.workoutintervaltimer;

import android.content.Context;

import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.workoutintervaltimer", appContext.getPackageName());
    }

    @Test
    public void clickStartButton() throws Exception{
        onView(withId(R.id.mainStartButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Intervals of :")));
    }

    @Test
    public void checkIntervalTimerInterval(){
        onView(withId(R.id.mainStartButton)).perform(click());
        onView(withId(R.id.workout_timer_edit_text)).perform(replaceText("20"));
        onView(withId(R.id.go_workout_timer_activity_button)).perform(click());
        onView(withId(R.id.interval_timer_interval_text)).check(matches(withText("Intervals of : 20")));
    }
}