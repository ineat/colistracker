package com.ineat.colistracker.domain.usecase;

import com.ineat.colistracker.domain.model.Courier;
import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.model.TrackingStatus;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import com.ineat.colistracker.domain.usecase.tracking.TrackingAppender;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrackingAppenderTest {

    @Mock
    TrackingRepository trackingRepository;

    TrackingAppender trackingAppender;

    @BeforeEach
    void init() {
        this.trackingAppender = new TrackingAppender(trackingRepository);
    }

    @Test
    @DisplayName("When creating tracking, should be activated by default")
    public void test() {
        // ARRANGE
        final Tracking tracking = Tracking.builder()
                .trackingId("TRCK")
                .courier(Courier.DPD)
                .name("Some tracking")
                .build();

        when(trackingRepository.create(any(Tracking.class)))
                .then(i -> {
                    final Tracking arg0 = i.getArgument(0, Tracking.class);
                    return Uni.createFrom().item(arg0.withId(1L));
                });

        // ACT
        final Tracking created = trackingAppender.execute(tracking)
                .await().indefinitely();

        //ASSERT
        assertThat(created)
                .extracting(t -> t.status)
                .isEqualTo(TrackingStatus.ACTIVE);
    }
}