package com.ineat.colistracker.rest;

import com.ineat.colistracker.dto.LaPosteTrackingDTO;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(LaPosteMockAPI.class)
public class LaPosteFetcherTest {

    @Inject
    LaPosteFetcher laPosteFetcher;

    @Test
    public void simpleTest() {
        // ARRANGE

        // ACT
        final LaPosteTrackingDTO indefinitely = laPosteFetcher.test("1K36275770836").await().indefinitely();

        // ASSERT
        assertThat(indefinitely)
                .extracting(
                        t -> t.returnCode,
                        t -> t.lang,
                        t -> t.scope
                ).containsExactly("200", "fr_FR", "open");

    }
}