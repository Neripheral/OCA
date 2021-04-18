package com.nerpage.oca.classes.fighting.actions;

import android.content.Context;

import androidx.annotation.NonNull;

import com.nerpage.oca.classes.Entity;
import com.nerpage.oca.classes.Identifiable;
import com.nerpage.oca.classes.fighting.Status;

public abstract class Action implements Cloneable, Identifiable {
    //================================================================================
    // region //            Inner classes

    public interface HasEffectAnimation{
        enum Duration{
            SHORT(50),
            MEDIUM(80),
            LONG(120);

            int value;

            Duration(int duration){
                this.value = duration;
            }
        }

        enum Scale{
            SMALL(0.5f),
            MEDIUM(0.8f),
            LARGE(1.0f);

            float value;

            Scale(float scale){
                this.value = scale;
            }
        }

        int getEffectResId();
        int getEffectDuration();
        float getEffectScale();
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private Entity source;
    private Entity target;

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    public Entity getSource() {
        return source;
    }

    public Action setSource(Entity source) {
        this.source = source;
        return this;
    }

    public Entity getTarget() {
        return target;
    }

    public Action setTarget(Entity target) {
        this.target = target;
        return this;
    }


    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Methods


    @Override
    public String getPrefix() {
        return "action";
    }

    @NonNull
    @Override
    public Action clone() {
        try {
            Action toReturn = (Action)super.clone();
            toReturn.setTarget(this.getTarget());
            toReturn.setSource(this.getSource());
            return toReturn;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract Status getAppliedStatus();
    public abstract int getTimeSpan();
    public abstract int getThumbnailResId();
    public abstract int getDescriptionResId();

    public String getDescription(Context context){
        return context.getString(getDescriptionResId());
    }

    // endregion //         Methods
    //================================================================================
}
