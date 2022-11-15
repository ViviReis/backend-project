package com.pipefy.grapql;

import com.github.javafaker.Faker;
import com.pipefy.commons.GraphQLQuery;
import com.pipefy.pojos.PipePojo;
import com.pipefy.config.ConfigurationManager;
import com.pipefy.hooks.BaseHooks;
import com.pipefy.utils.CryptUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.pipefy.commons.TestTags.CREATE_PIPE_TAG;
import static com.pipefy.config.DefaultConstants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Create Pipe")
@DisplayName("Pipe Tests")
@Tag(CREATE_PIPE_TAG)
public class PipeTests extends BaseHooks {
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private static Integer userId = Integer.valueOf(configurationManager.getUserId());
    private static String accessToken = CryptUtils.decrypt(configurationManager.getUserJwtToken(), configurationManager.getAutomationSecretKey());

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create pipe successfully")
    public void createPipeFromScratch() {
        Faker faker = new Faker();
        String name = String.valueOf(faker.name().fullName());
        GraphQLQuery query = new GraphQLQuery();
        query.setQuery("mutation CreatePipe($id: ID!, $name: String!) {createPipe(input: {name: $name, organization_id: $id}) {pipe {id name __typename}  __typename}}");
        query.setOperationName(CREATE_PIPE);
        PipePojo pipe = new PipePojo(
                userId,
                name
        );
        query.setVariables(pipe);
        given().
            log().
            all().
            filter(new AllureRestAssured()).
            header("Authorization", "Bearer ".concat(accessToken)).
            contentType(ContentType.JSON).
            body(query).
        when().
            post(INTERNAL_PATH).
        then().
            statusCode(SC_OK).
            body("data.createPipe.pipe.id", notNullValue()).
            body("data.createPipe.pipe.name", equalTo(pipe.getName())).
            body("data.createPipe.pipe.__typename", equalTo("Pipe")).
            log().
            all();
    }
}
