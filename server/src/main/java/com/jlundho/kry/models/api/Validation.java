package com.jlundho.kry.models.api;

public class Validation {

    private String failureReason;
    private boolean failed;

    public Validation() {

    }

    public Validation(boolean failed, String failureReason) {
        this.failed = failed;
        this.failureReason = failureReason;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }
}
