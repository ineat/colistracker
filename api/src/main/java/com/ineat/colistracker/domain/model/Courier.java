package com.ineat.colistracker.domain.model;

import lombok.Getter;
import org.eclipse.microprofile.config.ConfigProvider;

public enum Courier {
    LA_POSTE("La Poste", "colis_tracker.trackers.la_poste"),
    DPD("DPD", "colis_tracker.trackers.dpd"),
    MONDIAL_RELAY("Mondial Relay", "colis_tracker.trackers.mondial_relay");

    @Getter
    private final String name;
    private final String configProperty;

    Courier(String name, String configProperty) {
        this.name = name;
        this.configProperty = configProperty;
    }

    public boolean isActive() {
        final String activeConfigPropertyKey = String.format("%s.active", configProperty);

        return ConfigProvider.getConfig()
                .getOptionalValue(activeConfigPropertyKey, Boolean.class)
                .orElse(false);
    }
}
