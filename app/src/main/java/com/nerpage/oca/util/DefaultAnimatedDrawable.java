package com.nerpage.oca.util;

class DefaultAnimatedDrawable implements AnimatedDrawable {
    private final int resourceId;
    private final float speed;
    private final float scaleX;
    private final float scaleY;
    private final Callback onAnimationEndCallback;

    @Override
    public int getResourceId() {
        return resourceId;
    }

    @Override
    public float getSpeedMultiplier() {
        return speed;
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }

    @Override
    public float getScaleY() {
        return scaleY;
    }

    @Override
    public Callback onAnimationEnd() {
        return onAnimationEndCallback;
    }

    public DefaultAnimatedDrawable(int resourceId, float speed, float scaleX, float scaleY, Callback onAnimationEndCallback) {
        this.resourceId = resourceId;
        this.speed = speed;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.onAnimationEndCallback = onAnimationEndCallback;
    }

    public static class Builder implements AnimatedDrawable.Builder{
        private int resourceId;
        private float speed = 1f;
        private float scaleX = 1f;
        private float scaleY = 1f;
        private Callback onAnimationEndCallback = Callback.EMPTY;

        public Builder resourceId(int resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder speedMultiplier(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder scaleX(float scaleX) {
            this.scaleX = scaleX;
            return this;
        }

        public Builder scaleY(float scaleY) {
            this.scaleY = scaleY;
            return this;
        }

        public Builder scale(float scale){
            scaleX(scale);
            scaleY(scale);
            return this;
        }

        public Builder onAnimationEndCallback(Callback onAnimationEndCallback) {
            this.onAnimationEndCallback = onAnimationEndCallback;
            return this;
        }

        public AnimatedDrawable build(){
            return new DefaultAnimatedDrawable(resourceId, speed, scaleX, scaleY, onAnimationEndCallback);
        }

        public Builder(int resourceId) {
            this.resourceId = resourceId;
        }
    }
}
