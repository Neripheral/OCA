package com.nerpage.oca.modelfactories;

import android.content.Context;

import com.nerpage.oca.classes.fighting.Action;
import com.nerpage.oca.models.ActionCardModel;

public final class ActionCardModelFactory {
    public static ActionCardModel generateFreshModel(Context context, Action action) {
        ActionCardModel actionCardModel = new ActionCardModel();

        actionCardModel.setTitle(action.getName(context));
        actionCardModel.setThumbnailResId(action.getThumbnailResId());
        actionCardModel.setDescription(action.getDescription(context));

        return actionCardModel;
    }
}
