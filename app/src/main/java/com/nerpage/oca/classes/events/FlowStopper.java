package com.nerpage.oca.classes.events;

public class FlowStopper extends EventController{
    //================================================================================
    // region //            Inner classes

    private static class FlowEvent extends Event{
        private final Object identifier;

        private FlowEvent(Object identifier) {
            this.identifier = identifier;
        }
    }

    public static class StopFlowEvent extends FlowEvent{
        public StopFlowEvent(Object identifier){
            super(identifier);
        }
    }

    public static class ResumeFlowEvent extends FlowEvent {
        private ResumeFlowEvent(Object identifier){
            super(identifier);
        }
    }

    // endregion //         Inner classes
    //================================================================================
}
