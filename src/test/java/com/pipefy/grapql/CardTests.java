package com.pipefy.grapql;

import com.pipefy.commons.GraphQLQuery;
import com.pipefy.config.ConfigurationManager;
import com.pipefy.hooks.BaseHooks;
import com.pipefy.pojos.CardPojo;
import com.pipefy.utils.CryptUtils;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.pipefy.commons.TestTags.CREATE_CARD_TAG;
import static com.pipefy.config.DefaultConstants.CORE_PATH;
import static com.pipefy.config.DefaultConstants.CREATE_CARD;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Feature("Create Card")
@DisplayName("Card Tests")
@Tag(CREATE_CARD_TAG)
public class CardTests extends BaseHooks {
    private static ConfigurationManager configurationManager = ConfigurationManager.getInstance();
    private static String accessToken = CryptUtils.decrypt(configurationManager.getUserJwtToken(), configurationManager.getAutomationSecretKey());
    private static String pipeUuid = configurationManager.getPipeUuid();
    private static String phaseUuid = configurationManager.getPhaseUuid();
    private static String userId = String.valueOf(Integer.valueOf(configurationManager.getUserId()));
    private static boolean includeParentCards = false;

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Create card successfully")
    public void createCardSuccessfully() {
        GraphQLQuery query = new GraphQLQuery();
        query.setQuery("mutation createCard($pipeUuid: ID!, $assigneeIds: [ID], $attachments: [String], $dueDate: DateTime, $fields: [FieldValueInput], $labelIds: [ID], $parentUuids: [ID], $phaseUuid: ID, $throughConnectors: ReferenceConnectorFieldCoreInput, $includeParentCards: Boolean!) {createCard(input: {pipeUuid: $pipeUuid, phaseUuid: $phaseUuid, assigneeIds: $assigneeIds, attachments: $attachments, dueDate: $dueDate, fields: $fields, labelIds: $labelIds, parentUuids: $parentUuids, throughConnectors: $throughConnectors}) {card {id uuid suid title url pipe {id organization { id userRole __typename} __typename} assignees: lastAssignees {id name avatarUrl __typename} labels {id name color __typename} createdAt due_date: dueDate columns {field {id: internal_id __typename} name value array_value date_value datetime_value assignee_values {id name avatarUrl __typename} label_values {id color name __typename} connectedRepoItems  {... on PublicCard {title icon { name color __typename} __typename} ... on PublicTableRecord {title icon {name color __typename} __typename} __typename} __typename} summary {title value __typename} current_phase: currentPhase {id color name __typename} current_phase_age age updated_at: updatedAt parentCards @include(if: $includeParentCards) {id __typename} __typename} __typename}}");
        query.setOperationName(CREATE_CARD);
        CardPojo card = new CardPojo(
                pipeUuid,
                phaseUuid,
                includeParentCards
        );
        query.setVariables(card);
        given().
            log().
            all().
            filter(new AllureRestAssured()).
            header("Authorization", "Bearer ".concat(accessToken)).
            contentType(ContentType.JSON).
            body(query).
        when().
            post(CORE_PATH).
        then().
            statusCode(SC_OK).
            body("data.createCard.card.id", notNullValue()).
            body("data.createCard.card.uuid", notNullValue()).
            body("data.createCard.card.pipe.organization.id", equalTo(userId)).
            body("data.createCard.card.__typename", equalTo("Card")).
            log().
            all();
    }
}
