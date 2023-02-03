package com.nerpage.oca.pac;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Objects;
import java.util.Optional;

/**
 * Abstraction of the controller of a node. Responsible for data transfers and logic.
 * Uses {@code Controller}'s specific {@code Model} class and {@code Presenter} class.
 * @param <M> {@code Model} responsible for handling node's data storage.
 * @param <P> {@code Presenter} responsible for handling node's ui manipulation.
 */
public abstract class AbstractController<M extends Model, P extends Presenter> extends Fragment {
    /**
     * {@code Factory} responsible for creating this node's {@code Presenter}s when needed.
     * If null {@code Presenter}s will not be created.
     * For DI and decoupling purposes {@code Presenter} should be initialised via {@code Factory}.
     */
    @Nullable
    private Presenter.Factory<P> presenterFactory;

    /**
     * Setter changing this {@code Controller}'s {@code Presenter Factory}.
     * @param presenterFactory new {@code Presenter Factory}. May be null.
     */
    protected void setPresenterFactory(@Nullable Presenter.Factory<P> presenterFactory){
        this.presenterFactory = presenterFactory;
    }

    /**
     * Returns this {@code Controller}'s {@code Presenter Factory}. May be null thus the return
     * value should be checked before using.
     * @return {@code Optional} of {@code Factory}. Present if {@code this} has one.
     */
    protected Optional<? extends Presenter.Factory<P>> getPresenterFactory(){
        return Optional.ofNullable(presenterFactory);
    }



    /**
     * {@code Presenter} responsible for handling UI changes for this node.
     * If null, no changes to the UI will be made.
     */
    @Nullable
    private P presenter;

    /**
     * Returns this {@code Controller}'s {@code Presenter}. May be null thus the return
     * value should be checked before using.
     * @return {@code Optional} of {@code Presenter}. Present if {@code this} has one.
     */
    protected Optional<P> getPresenter() {
        return Optional.ofNullable(presenter);
    }

    /**
     * Returns this {@code Controller}'s {@code Presenter} or throws {@code InvalidStateException}
     * if it doesn't exist. Use only if absolutely sure the {@code Presenter} exists and the lack
     * of thereof should mean code malfunctions.
     * @return {@code Presenter} of this node.
     */
    protected P requirePresenter(){
        return getPresenter().orElseThrow(
                ()->new IllegalStateException("Presenter required but isn't available."));
    }

    /**
     * Setter changing this {@code Controller}'s responsible {@code Presenter}.
     * @param presenter new {@code Presenter}. May be null.
     */
    protected void setPresenter(@Nullable P presenter) {
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


    /**
     * {@code Model} responsible for storing node's data and informing {@code Controller} of
     * any data changes. If null, no data will be saved or read.
     */
    private M model;

    /**
     * Returns this {@code Controller}'s {@code Model}. May be null thus the return
     * value should be checked before using.
     * @return {@code Optional} of {@code Model}. Present if {@code this} has one.
     */
    protected Optional<M> getModel(){
        return Optional.ofNullable(model);
    }

    /**
     * Returns this {@code Controller}'s {@code Model} or throws {@code InvalidStateException}
     * if it doesn't exist. Use only if absolutely sure the {@code Model} exists and the lack
     * of thereof should mean code malfunctions.
     * @return {@code Model} of this node.
     */
    protected M requireModel(){
        return getModel().orElseThrow(
                ()->new IllegalStateException("Model required but isn't available."));
    }

    /**
     * Setter changing this {@code Controller}'s responsible {@code Model}.
     * @param model new {@code Model}. May be null.
     */
    protected void setModel(M model){
        this.model = model;
    }
}
