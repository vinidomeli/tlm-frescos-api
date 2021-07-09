package com.mercadolibre.dambetan01.integration;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BatchControllerTest {
    @LocalServerPort
    private int port;

    @Test
    void findBatchesByProductId() {
        Response response = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .param("productId", 1)
                .when()
                .get("/api/v1/fresh-products/list")
                .then()
                .extract().response();

        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("656a1cee-9a14-49a0-921e-a7e0ae692918", response.jsonPath().getString("[0].section_dto[0].section_code"));
//        Assertions.assertEquals("b99246ba-eb0c-47f4-beb4-1e48ccfbb7d7", response.jsonPath().getString("[0].section_dto[0].warehouse_code"));
//        Assertions.assertEquals(1L, response.jsonPath().getLong("[0].product_id"));
//        Assertions.assertEquals("1", response.jsonPath().getString("[0].batch_stock[0].productId"));
//        Assertions.assertEquals("10.0", response.jsonPath().getString("[0].batch_stock[0].currentTemperature"));
//        Assertions.assertEquals("10.0", response.jsonPath().getString("[0].batch_stock[0].minimumTemperature"));
//        Assertions.assertEquals("3", response.jsonPath().getString("[0].batch_stock[0].initialQuantity"));
//        Assertions.assertEquals("3", response.jsonPath().getString("[0].batch_stock[0].currentQuantity"));
//        Assertions.assertEquals("2021-07-03", response.jsonPath().getString("[0].batch_stock[0].manufacturingDate"));
//        Assertions.assertEquals("2021-07-03 10:30", response.jsonPath().getString("[0].batch_stock[0].manufacturingTime"));
//        Assertions.assertEquals("2021-07-31", response.jsonPath().getString("[0].batch_stock[0].dueDate"));
    }

    @Test
    void findBatchesByProductIdDoesntExist() {
        Response response = given()
                .port(this.port)
                .contentType(ContentType.JSON)
                .param("productId", 123)
                .when()
                .get("/api/v1/fresh-products/list")
                .then()
                .extract().response();

        Assertions.assertEquals(404, response.statusCode());
    }

//    @Test
//    void getBatchStockOrderedByDueDate() {
//    }
//
//    @Test
//    void testGetBatchStockOrderedByDueDate() {
//    }
//
//    @Test
//    void findProductInWarehouses() {
//    }
}