package com.nerpage.oca.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withTagKey;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.ActionCardController;
import com.nerpage.oca.pac.controllers.implementation.DefaultActionCardController;
import com.nerpage.oca.pac.presenters.ActionCardPresenter.POI;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
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
    @EnumSource(value = POI.class, names = {"TITLE", "THUMBNAIL", "DESCRIPTION", "INFO_BOX"})
    public void dataIsNotVisibleWhenJustInitiated(POI poi){
        onView(withId(poi.getId())).check(doesNotExist());
    }

    @ParameterizedTest
    @EnumSource(value = POI.class, names = {"TITLE", "THUMBNAIL", "DESCRIPTION", "INFO_BOX"})
    public void allViewsAreVisibleAtStart(POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(poi.getId())).check(matches(isDisplayed()));
    }
    
    @ParameterizedTest
    @EnumSource(value = POI.class, names = {"DESCRIPTION", "INFO_BOX"})
    public void someViewsGetsHiddenWhenDetailsClosed(POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onFragment(f->f.setIsDescriptionBoxOpen(false));
        onView(withId(poi.getId())).check(matches(Matchers.not(isDisplayed())));
    }

    @ParameterizedTest
    @EnumSource(value = POI.class, names = {"TITLE", "THUMBNAIL"})
    public void someViewsAreStillVisibleWhenDetailClosed(POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onFragment(f->f.setIsDescriptionBoxOpen(false));
        onView(withId(poi.getId())).check(matches(isDisplayed()));
    }

    @ParameterizedTest
    @EnumSource(value = POI.class, names = {"DESCRIPTION", "INFO_BOX"})
    public void someViewsGetsRevealedWhenDetailsOpened(POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onFragment(f->f.setIsDescriptionBoxOpen(false));
        scenario.onFragment(f->f.setIsDescriptionBoxOpen(true));
        onView(withId(poi.getId())).check(matches(isDisplayed()));
    }

    public static final String CHANGED_TITLE = "New title";
    public static final Integer CHANGED_THUMBNAIL = android.R.drawable.btn_star;
    public static final String CHANGED_DESCRIPTION = "New Description";

    @ParameterizedTest
    @EnumSource(value = POI.class, names = {"TITLE", "THUMBNAIL", "DESCRIPTION"})
    public void viewsUpdateCorrectlyWhenDataIsUpdated(POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onFragment(f-> {
            switch(poi){
                case TITLE -> f.setTitle(CHANGED_TITLE);
                case DESCRIPTION -> f.setDescription(CHANGED_DESCRIPTION);
                case THUMBNAIL -> f.setThumbnailResId(CHANGED_THUMBNAIL);
            }
        });

        switch(poi){
            case TITLE -> onView(withId(poi.getId())).check(matches(withText(CHANGED_TITLE)));
            case DESCRIPTION -> onView(withId(poi.getId())).check(matches(withText(CHANGED_DESCRIPTION)));
            case THUMBNAIL -> onView(withId(poi.getId()))
                    .check(matches(withTagKey(R.id.testing_image_resId, is(CHANGED_THUMBNAIL))));
        }
    }

}
