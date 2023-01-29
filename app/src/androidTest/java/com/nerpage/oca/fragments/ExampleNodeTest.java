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
import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.pac.presenters.DefaultExamplePresenter;
import com.nerpage.oca.pac.presenters.ExamplePresenter;
import com.nerpage.oca.pac.presenters.PresentersUtilisingExamplePresenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ExampleNodeTest {
    public FragmentScenario<ExampleFragment> scenario;

    @BeforeEach
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(
                ExampleFragment.class,
                null,
                R.style.AppTheme,
                Lifecycle.State.INITIALIZED);
        //scenario.recreate();
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
        onView(withText(R.string.example_fragment_title)).check(matches(isDisplayed()));
    }

    @Test
    public void titleChangesCorrectlyWhenConfirmButtonPressed() {
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(R.id.f_example_inputText)).perform(ViewActions.typeText("Success"));
        onView(withId(R.id.f_example_confirmButton)).perform(ViewActions.click());
        onView(withId(R.id.f_example_title)).check(matches(withText("Success")));
    }


}