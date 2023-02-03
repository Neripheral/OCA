package com.nerpage.oca.fragments;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.controllers.ExampleController;
import com.nerpage.oca.pac.controllers.implementation.DefaultActionCardController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
