package com.nerpage.oca.classes.events;

public class FlowController extends EventController{
    //================================================================================
    // region //            Inner classes

    public static class FlowHelper {
        private final EventController.EventListener listener;

        public void stopFlow(){
            listener.emitEvent(new StopFlow(this));
        }

        public void startFlow(){
            listener.emitEvent(new StartFlow(this));
        }

        public FlowHelper(EventController.EventListener flowListener){
            listener = flowListener;
        }
    }

    private static class FlowEvent extends Event{
        private final Object identifier;

        public Object getIdentifier(){
            return identifier;
        }

        private FlowEvent(Object identifier) {
            this.identifier = identifier;
        }
    }

    public static class StopFlow extends FlowEvent{
        public StopFlow(Object identifier){
            super(identifier);
        }
    }

    public static class StartFlow extends FlowEvent {
        public StartFlow(Object identifier){
            super(identifier);
        }
    }

    // endregion //         Inner classes
    //================================================================================
}
