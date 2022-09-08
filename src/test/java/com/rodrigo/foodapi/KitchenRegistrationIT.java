package com.rodrigo.foodapi;

import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.repository.KitchenRepository;
import com.rodrigo.foodapi.util.DatabaseCleaner;
import com.rodrigo.foodapi.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class KitchenRegistrationIT {
    private static final int COZINHA_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private KitchenRepository kitchenRepository;

    private Kitchen cozinhaAmericana;
    private int quantidadeCozinhasCadastradas;
    private String jsonCorretoCozinhaChinesa;

    @BeforeEach
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/kitchens";

        jsonCorretoCozinhaChinesa = ResourceUtils.getContentFromResource(
                "/json/correto/cozinha-chinesa.json");

        databaseCleaner.clearTables();
        prepareData();
    }

    @Test
    void shouldReturnStatus200_WhenSearchKitchens() {

        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldReturnCountCorrectKitchens_WhenSearchKitchens() {

        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then()
                .body("", hasSize(quantidadeCozinhasCadastradas));
    }


    @Test
    void shouldReturnStatus201_WhenRegistrationKitchens() {
        given()
                .body(jsonCorretoCozinhaChinesa)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnCorrectAnswerAndStatus_WhenConsultExistingKitchen() {
        given()
                .pathParam("kitchenId", cozinhaAmericana.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/kitchen/{kitchenId}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(cozinhaAmericana.getName()));
    }

    @Test
    public void shouldReturnStatus404_When_to_ConsultNonExistentKitchen() {
        given()
                .pathParam("kitchenId", COZINHA_ID_INEXISTENTE)
                .accept(ContentType.JSON)
                .when()
                .get("/{kitchenId}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepareData() {
        Kitchen cozinhaTailandesa = new Kitchen();
        cozinhaTailandesa.setName("Tailandesa");
        kitchenRepository.save(cozinhaTailandesa);

        cozinhaAmericana = new Kitchen();
        cozinhaAmericana.setName("Americana");
        kitchenRepository.save(cozinhaAmericana);

        quantidadeCozinhasCadastradas = (int) kitchenRepository.count();
    }
}
