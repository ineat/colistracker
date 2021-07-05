package com.ineat.colistracker.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@Slf4j
public class LaPosteMockAPI implements QuarkusTestResourceLifecycleManager {

	private WireMockServer wireMockServer;

	@Override
	public Map<String, String> start() {
		log.info("Setting up mock La Poste API");
		wireMockServer = new WireMockServer();
		wireMockServer.start();

		stubFor(get(urlEqualTo("/suivi/v2/idships/1K36275770836"))
				.willReturn(aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(
								"{\n" +
										"    \"lang\": \"fr_FR\",\n" +
										"    \"scope\": \"open\",\n" +
										"    \"returnCode\": 200,\n" +
										"    \"shipment\": {\n" +
										"        \"holder\": 1,\n" +
										"        \"idShip\": \"1K36275770836\",\n" +
										"        \"product\": \"Lettre Suivie\",\n" +
										"        \"isFinal\": false,\n" +
										"        \"deliveryDate\": \"2020-03-18T00:00:00+01:00\",\n" +
										"        \"entryDate\": \"2020-03-14T00:00:00+01:00\",\n" +
										"        \"timeline\": [\n" +
										"            {\n" +
										"                \"id\": 1,\n" +
										"                \"shortLabel\": \"Pris en charge par La Poste\",\n" +
										"                \"longLabel\": \"\",\n" +
										"                \"date\": \"2020-03-14T00:00:00+01:00\",\n" +
										"                \"country\": \"\",\n" +
										"                \"status\": true,\n" +
										"                \"type\": 1\n" +
										"            },\n" +
										"            {\n" +
										"                \"id\": 2,\n" +
										"                \"shortLabel\": \"En cours d'acheminement\",\n" +
										"                \"longLabel\": \"\",\n" +
										"                \"country\": \"\",\n" +
										"                \"status\": true,\n" +
										"                \"type\": 1\n" +
										"            },\n" +
										"            {\n" +
										"                \"id\": 3,\n" +
										"                \"shortLabel\": \"Arrivé sur le site de distribution\",\n" +
										"                \"longLabel\": \"\",\n" +
										"                \"country\": \"\",\n" +
										"                \"status\": false,\n" +
										"                \"type\": 1\n" +
										"            },\n" +
										"            {\n" +
										"                \"id\": 4,\n" +
										"                \"shortLabel\": \"Courrier en distribution\",\n" +
										"                \"longLabel\": \"\",\n" +
										"                \"country\": \"\",\n" +
										"                \"status\": false,\n" +
										"                \"type\": 1\n" +
										"            },\n" +
										"            {\n" +
										"                \"id\": 5,\n" +
										"                \"shortLabel\": \"Courrier distribué\",\n" +
										"                \"longLabel\": \"Votre courrier est déposé dans la boîte à lettres du destinataire.\",\n" +
										"                \"date\": \"2020-03-18T00:00:00+01:00\",\n" +
										"                \"country\": \"\",\n" +
										"                \"status\": true,\n" +
										"                \"type\": 1\n" +
										"            }\n" +
										"        ],\n" +
										"        \"event\": [\n" +
										"            {\n" +
										"                \"order\": 100,\n" +
										"                \"date\": \"2020-03-14T00:00:00+01:00\",\n" +
										"                \"label\": \"Votre courrier a été remis à La Poste par l'expéditeur.\",\n" +
										"                \"code\": \"PC1\"\n" +
										"            }\n" +
										"        ],\n" +
										"        \"url\": \"https://www.laposte.fr/outils/suivre-vos-envois?code=1K36275770836\"\n" +
										"    }\n" +
										"}"
						)));

		return Map.of(
				"laposte-api/mp-rest/url", wireMockServer.baseUrl(),
				"laposte.api.key", "MOCK_API_KEY"
		);
	}

	@Override
	public void stop() {
		if (null != wireMockServer) {
			wireMockServer.stop();  
		}
	}
}