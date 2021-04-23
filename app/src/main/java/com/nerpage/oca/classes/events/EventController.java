package com.nerpage.oca.classes.events;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class EventController {
    //================================================================================
    // region //            Inner classes

    public interface EventReceiver{
        void onEventReceived(Event event);
    }

    public interface EventEmitter{
        void setEventListener(EventListener eventListener);
    }

    public interface EventListener{
        void emitEvent(Event event);
    }

    // endregion //         Inner classes
    //================================================================================
    //================================================================================
    // region //            Fields

    private final List<EventReceiver> receivers = new ArrayList<>();

    // endregion //         Fields
    //================================================================================
    //================================================================================
    // region //            Accessors

    private List<EventReceiver> getReceivers() {
        return receivers;
    }

    // endregion //         Accessors
    //================================================================================
    //================================================================================
    // region //            Private methods

    private void onEventReceived(Event event){
        for(EventReceiver receiver : getReceivers()){
            receiver.onEventReceived(event);
        }
    }

    // endregion //         Private methods
    //================================================================================
    //================================================================================
    // region //            Interface

    public void addEventReceiver(EventReceiver receiver){
        getReceivers().add(receiver);
    }

    public void addEventEmitter(EventEmitter emitter){
        emitter.setEventListener(this::onEventReceived);
    }

    // endregion //         Interface
    //================================================================================
    //================================================================================
    // region //            Constructors

    public EventController(){}

    // endregion //         Constructors
    //================================================================================
}
