package com.nerpage.oca.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static com.google.common.truth.Truth.assertThat;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.action.ViewActions;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.ExampleController;
import com.nerpage.oca.pac.controllers.implementation.DefaultExampleController;
import com.nerpage.oca.pac.presenters.ExamplePresenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExampleNodeTest {
    public FragmentScenario<? extends ExampleController> scenario;

    @BeforeEach
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(
                DefaultExampleController.class,
                null,
                R.style.AppTheme,
                Lifecycle.State.INITIALIZED);
    }

    @Test
    public void presenterKnowsWhichLayoutItDescribes(){
        int layoutId = ExamplePresenter.getDescribedLayoutId();
        assertThat(layoutId).isEqualTo(R.layout.fragment_example);
    }

    @Test
    public void viewIsNotVisibleAtStart() {
        scenario.moveToState(Lifecycle.State.CREATED);
        Assertions.assertThrows(NoMatchingViewException.class,
                () -> onView(withId(R.id.root_example)).perform(click())
        );
    }

    @Test
    public void viewIsVisibleWhenInResumedState(){
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withText("Example Fragment")).check(matches(isDisplayed()));
    }

    @Test
    public void titleChangesCorrectlyWhenConfirmButtonPressed() {
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(R.id.f_example_inputText)).perform(ViewActions.typeText("New Title"));
        onView(withId(R.id.f_example_confirmButton)).perform(ViewActions.click());
        onView(withId(R.id.f_example_title)).check(matches(withText("New Title")));
    }

    @Test
    public void titleRemainsAfterStateChange(){
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(R.id.f_example_inputText)).perform(ViewActions.typeText("New Title"));
        onView(withId(R.id.f_example_confirmButton)).perform(ViewActions.click());
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(R.id.f_example_title)).check(matches(withText("New Title")));
    }
}