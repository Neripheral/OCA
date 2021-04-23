package com.nerpage.oca.classes.events;

public class FlowFreezer extends EventController{
    //================================================================================
    // region //            Inner classes

    private static class FlowEvent extends Event{
        private final Object identifier;

        public Object getIdentifier(){
            return identifier;
        }

        private FlowEvent(Object identifier) {
            this.identifier = identifier;
        }
    }

    public static class FreezeFlow extends FlowEvent{
        public FreezeFlow(Object identifier){
            super(identifier);
        }
    }

    public static class ResumeFlow extends FlowEvent {
        public ResumeFlow(Object identifier){
            super(identifier);
        }
    }

    // endregion //         Inner classes
    //================================================================================
}
