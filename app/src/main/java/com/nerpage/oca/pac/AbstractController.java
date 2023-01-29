package com.nerpage.oca.pac;

import androidx.fragment.app.Fragment;

import com.nerpage.oca.fragments.Model;

import java.util.Optional;

public abstract class AbstractController<M extends Model, P extends Presenter> extends Fragment {
    private Presenter.AbstractFactory<P> presenterFactory;

    public void setPresenterFactory(Presenter.AbstractFactory<P> presenterFactory){
        this.presenterFactory = presenterFactory;
    }

    protected Optional<? extends Presenter.AbstractFactory<P>> getPresenterFactory(){
        return Optional.ofNullable(presenterFactory);
    }


    private P presenter;

    protected Optional<P> getPresenter() {
        return Optional.ofNullable(presenter);
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }


    private M model;

    protected Optional<M> getModel(){
        return Optional.ofNullable(model);
    }

    protected void setModel(M model){
        this.model = model;
    }
}
