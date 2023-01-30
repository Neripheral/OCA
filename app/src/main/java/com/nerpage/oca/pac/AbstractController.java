package com.nerpage.oca.pac;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractController<M extends Model, P extends Presenter> extends Fragment {
    private Presenter.Factory<P> presenterFactory;

    public void setPresenterFactory(Presenter.Factory<P> presenterFactory){
        this.presenterFactory = presenterFactory;
    }

    protected Optional<? extends Presenter.Factory<P>> getPresenterFactory(){
        return Optional.ofNullable(presenterFactory);
    }


    private P presenter;

    protected Optional<P> getPresenter() {
        return Optional.ofNullable(presenter);
    }

    protected P requirePresenter(){
        return getPresenter().orElseThrow(
                ()->new IllegalStateException("Presenter required but isn't available."));
    }

    protected void setPresenter(P presenter) {
        this.presenter = presenter;
    }


    /**
     * Attempts to create a new {@code Presenter} from {@code Factory} object and binds it to this
     * {@code Controller}. If there is no {@code Factory} set, function does nothing.
     *
     * @param root View being the root of a hierarchy that the {@code Presenter} is supposed to be
     *             based on. Cannot be null.
     * @return {@code Optional} of {@code Presenter}. Present if it has been created successfully.
     */
    protected Optional<P> tryBuildPresenter(@NonNull View root){
        Objects.requireNonNull(root);
        Optional<P> p = getPresenterFactory().map(f -> f.createFor(root));
        p.ifPresent(this::setPresenter);
        return p;
    }


    private M model;

    protected Optional<M> getModel(){
        return Optional.ofNullable(model);
    }

    protected M requireModel(){
        return getModel().orElseThrow(
                ()->new IllegalStateException("Model required but isn't available."));
    }

    protected void setModel(M model){
        this.model = model;
    }
}
