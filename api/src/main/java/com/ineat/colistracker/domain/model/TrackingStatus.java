package com.ineat.colistracker.domain.model;

/**
 * User defined status for the tracking. This
 * defines if we watch for updates for the tracking or not
 */
public enum TrackingStatus {
    /**
     * Active. We'll watch for update for the tracking
     */
    ACTIVE,
    /**
     * Archived. We won't watch for updates for the tracking
     */
    ARCHIVED
}
