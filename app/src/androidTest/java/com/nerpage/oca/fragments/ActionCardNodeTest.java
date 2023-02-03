package com.nerpage.oca.fragments;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.controllers.ExampleController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionCardNodeTest {
    public FragmentScenario<ActionCardController> scenario;

    @BeforeEach
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(
                ActionCardController.class,
                null,
                R.style.AppTheme,
                Lifecycle.State.INITIALIZED);
    }

    @Test
    void presenterKnowsWhichLayoutItDescribes() {

    }

    @Test
    public void allViewsAreVisibleAtStart(){

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
