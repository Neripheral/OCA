package com.nerpage.oca.fragments;

import com.nerpage.oca.pac.Presenter;
import com.nerpage.oca.pac.presenters.ExamplePresenter;
import com.nerpage.oca.pac.presenters.ExamplePresenterUtilizingPresenters;

import org.junit.jupiter.api.BeforeEach;

public class PresentersTest extends ExampleNodeTest{
    Presenter.Factory<ExamplePresenter> factory = new ExamplePresenterUtilizingPresenters.ExamplePresenterUtilizingPresentersFactory();

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        scenario.onFragment(f->f.setPresenterFactory(factory));
    }
}
