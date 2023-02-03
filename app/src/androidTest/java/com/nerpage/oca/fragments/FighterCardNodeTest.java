package com.nerpage.oca.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;

import com.nerpage.oca.R;
import com.nerpage.oca.pac.controllers.FighterCardController;
import com.nerpage.oca.pac.controllers.implementation.DefaultFighterCardController;
import com.nerpage.oca.pac.presenters.FighterCardPresenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class FighterCardNodeTest {
    public static final String NEW_TITLE = "New Enemy";
    public static final String NEW_CURRENT_BLOOD = "-111";
    public static final String NEW_MAX_BLOOD = "-200";

    public FragmentScenario<? extends FighterCardController> scenario;


    @BeforeEach
    public void setUp() {
        scenario = FragmentScenario.launchInContainer(
                DefaultFighterCardController.class,
                null,
                R.style.AppTheme,
                Lifecycle.State.INITIALIZED);
    }

    @ParameterizedTest
    @EnumSource(value = FighterCardPresenter.POI.class, names = {"TITLE", "CURRENT_BLOOD", "MAX_BLOOD"})
    public void dataIsNotVisibleWhenJustInitiated(FighterCardPresenter.POI poi){
        onView(withId(poi.getId())).check(doesNotExist());
    }

    @ParameterizedTest
    @EnumSource(value = FighterCardPresenter.POI.class, names = {"TITLE", "CURRENT_BLOOD", "MAX_BLOOD"})
    public void dataIsVisibleWhenResumed(FighterCardPresenter.POI poi){
        scenario.moveToState(Lifecycle.State.RESUMED);
        onView(withId(poi.getId())).check(matches(isDisplayed()));
    }

    @ParameterizedTest
    @EnumSource(value = FighterCardPresenter.POI.class, names = {"TITLE", "CURRENT_BLOOD", "MAX_BLOOD"})
    public void dataCanBeChanged(FighterCardPresenter.POI poi){
        Map<FighterCardPresenter.POI, String> map = new EnumMap<>(FighterCardPresenter.POI.class);
        map.put(FighterCardPresenter.POI.TITLE, NEW_TITLE);
        map.put(FighterCardPresenter.POI.CURRENT_BLOOD, NEW_CURRENT_BLOOD);
        map.put(FighterCardPresenter.POI.MAX_BLOOD, NEW_MAX_BLOOD);

        scenario.moveToState(Lifecycle.State.RESUMED);
        scenario.onFragment(f-> {
            switch (poi) {
                case TITLE -> f.updateTitle(map.get(poi));
                case CURRENT_BLOOD -> f.updateCurrentBlood(Integer.parseInt(Objects.requireNonNull(map.get(poi))));
                case MAX_BLOOD -> f.updateMaxBlood(Integer.parseInt(Objects.requireNonNull(map.get(poi))));
            }
        });
        onView(withId(poi.getId())).check(matches(withText(map.get(poi))));
    }


}
