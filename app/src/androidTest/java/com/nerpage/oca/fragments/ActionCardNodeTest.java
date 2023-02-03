package com.nerpage.oca.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.controllers.implementation.DefaultActionCardController;
import com.nerpage.oca.pac.presenters.ActionCardPresenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class ActionCardNodeTest {
    public FragmentScenario<? extends ActionCardController> scenario;

    @BeforeEach
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(
                DefaultActionCardController.class,
                null,
                R.style.AppTheme,
                Lifecycle.State.INITIALIZED);
    }

    @ParameterizedTest
    @EnumSource(value = ActionCardPresenter.POI.class, names = {"TITLE", "THUMBNAIL", "DESCRIPTION", "INFO_BOX"})
    public void dataIsNotVisibleWhenJustInitiated(ActionCardPresenter.POI poi){
        onView(withId(poi.getId())).check(doesNotExist());
    }

    @ParameterizedTest
    @EnumSource(value = ActionCardPresenter.POI.class, names = {"TITLE", "THUMBNAIL", "DESCRIPTION", "INFO_BOX"})
    public void allViewsAreVisibleAtStart(ActionCardPresenter.POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(poi.getId())).check(matches(isDisplayed()));
    }

    @Test
    public void someViewsGetsHiddenWhenDetailsClosed(){

    }

    @Test
    public void someViewsGetsRevealedWhenDetailsOpened(){

    }

    @Test
    public void viewsUpdateCorrectlyWhenDataIsUpdated(){

    }

}
