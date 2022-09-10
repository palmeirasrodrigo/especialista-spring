package com.rodrigo.foodapi.api.v1;

import com.rodrigo.foodapi.api.v1.controller.*;
import com.rodrigo.foodapi.domain.model.Demand;
import org.springframework.hateoas.*;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

    public static final TemplateVariables PAGINATION_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", VariableType.REQUEST_PARAM),
            new TemplateVariable("size", VariableType.REQUEST_PARAM),
            new TemplateVariable("sort", VariableType.REQUEST_PARAM));

    public static final TemplateVariables PROJECTION_VARIABLES = new TemplateVariables(
            new TemplateVariable("projection", VariableType.REQUEST_PARAM));

    public Link linkToDelivered(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("clientId", VariableType.REQUEST_PARAM),
                new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
                new TemplateVariable("createAt", VariableType.REQUEST_PARAM),
                new TemplateVariable("createEnd", VariableType.REQUEST_PARAM));

        String demandsUrl = linkTo(Demand.class).toUri().toString();

        return Link.of(UriTemplate.of(demandsUrl,
                PAGINATION_VARIABLES.concat(filterVariables)), LinkRelation.of(rel));
    }

    public Link linkToConfirmDemand(String demandCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class)
                .confirm(demandCode)).withRel(rel);
    }

    public Link linkToDeliveredDemand(String demandCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class)
                .delivered(demandCode)).withRel(rel);
    }

    public Link linkToCancelDemand(String demandCode, String rel) {
        return linkTo(methodOn(OrderFlowController.class)
                .cancel(demandCode)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .find(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurant(Long restaurantId) {
        return linkToRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurants(String rel) {
        String restaurantsUrl = linkTo(RestaurantController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantsUrl, PROJECTION_VARIABLES), rel);
    }

    public Link linkToRestaurants() {
        return linkToRestaurants(IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantFormPayment(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantFormPaymentController.class)
                .listAll(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantFormPayment(Long restaurantId) {
        return linkToRestaurantFormPayment(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToRestaurantLinkToRestaurantFormPaymentDisassociation(
            Long restaurantId, Long formPaymentId, String rel) {

        return linkTo(methodOn(RestaurantFormPaymentController.class)
                .disassociate(restaurantId, formPaymentId)).withRel(rel);
    }

    public Link linkToRestaurantLinkToRestaurantFormPaymentAssociation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantFormPaymentController.class)
                .associate(restaurantId, null)).withRel(rel);
    }

    public Link linkToRestaurantOpen(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .open(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantClose(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .close(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantInactivation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .inactive(restaurantId)).withRel(rel);
    }

    public Link linkToRestaurantActive(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantController.class)
                .active(restaurantId)).withRel(rel);
    }

    public Link linkToUser(Long userId, String rel) {
        return linkTo(methodOn(UserController.class)
                .find(userId)).withRel(rel);
    }

    public Link linkToUser(Long userId) {
        return linkToUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToUsers(String rel) {
        return linkTo(UserController.class).withRel(rel);
    }

    public Link linkToUsers() {
        return linkToUsers(IanaLinkRelations.SELF.value());
    }

    public Link linkToUserGroupAssociation(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .associate(userId, null)).withRel(rel);
    }

    public Link linkToUserGroupDisassociate(Long userId, Long grupoId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .disassociate(userId, grupoId)).withRel(rel);
    }

    public Link linkToGroupsUser(Long userId, String rel) {
        return linkTo(methodOn(UserGroupController.class)
                .list(userId)).withRel(rel);
    }

    public Link linkToGroupsUser(Long userId) {
        return linkToGroupsUser(userId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGroups(String rel) {
        return linkTo(GroupController.class).withRel(rel);
    }

    public Link linkToGroups() {
        return linkToGroups(IanaLinkRelations.SELF.value());
    }

    public Link linkToPermission(String rel) {
        return linkTo(PermissionController.class).withRel(rel);
    }

    public Link linkToPermission() {
        return linkToPermission(IanaLinkRelations.SELF.value());
    }

    public Link linkToGroupPermissions(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .list(groupId)).withRel(rel);
    }

    public Link linkToGroupPermissions(Long groupId) {
        return linkToGroupPermissions(groupId, IanaLinkRelations.SELF.value());
    }

    public Link linkToGroupPermissionAssociation(Long groupId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .associate(groupId, null)).withRel(rel);
    }

    public Link linkToGroupPermissionDisassociation(Long groupId, Long permissionId, String rel) {
        return linkTo(methodOn(GroupPermissionController.class)
                .disassociate(groupId, permissionId)).withRel(rel);
    }

    public Link linkToResponsibleRestaurant(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class)
                .listAll(restaurantId)).withRel(rel);
    }

    public Link linkToResponsibleRestaurant(Long restaurantId) {
        return linkToResponsibleRestaurant(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToResponsibleRestaurantDisassociation(
            Long restaurantId, Long userId, String rel) {

        return linkTo(methodOn(RestaurantResponsibleUserController.class)
                .disassociate(restaurantId, userId)).withRel(rel);
    }

    public Link linkToResponsibleRestaurantAssociation(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantResponsibleUserController.class)
                .associate(restaurantId, null)).withRel(rel);
    }

    public Link linkToFormPayment(Long formPaymentId, String rel) {
        return linkTo(methodOn(FormPaymentController.class)
                .find(formPaymentId, null)).withRel(rel);
    }

    public Link linkToFormPayment(Long formPaymentId) {
        return linkToFormPayment(formPaymentId, IanaLinkRelations.SELF.value());
    }

    public Link linkToFormsPayment(String rel) {
        return linkTo(FormPaymentController.class).withRel(rel);
    }

    public Link linkToFormsPayment() {
        return linkToFormsPayment(IanaLinkRelations.SELF.value());
    }

    public Link linkToCity(Long cityId, String rel) {
        return linkTo(methodOn(CityController.class)
                .find(cityId)).withRel(rel);
    }

    public Link linkToCity(Long cityId) {
        return linkToCity(cityId, IanaLinkRelations.SELF.value());
    }

    public Link linkToCities(String rel) {
        return linkTo(CityController.class).withRel(rel);
    }

    public Link linkToCities() {
        return linkToCities(IanaLinkRelations.SELF.value());
    }

    public Link linkToState(Long stateId, String rel) {
        return linkTo(methodOn(StateController.class)
                .find(stateId)).withRel(rel);
    }

    public Link linkToState(Long stateId) {
        return linkToState(stateId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStates(String rel) {
        return linkTo(StateController.class).withRel(rel);
    }

    public Link linkToStates() {
        return linkToStates(IanaLinkRelations.SELF.value());
    }

    public Link linkToProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .find(restaurantId, productId))
                .withRel(rel);
    }

    public Link linkToProduct(Long restaurantId, Long productId) {
        return linkToProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToProducts(Long restaurantId, String rel) {
        return linkTo(methodOn(RestaurantProductController.class)
                .listAll(restaurantId, false)).withRel(rel);
    }

    public Link linkToProducts(Long restaurantId) {
        return linkToProducts(restaurantId, IanaLinkRelations.SELF.value());
    }

    public Link linkToPhotoProduct(Long restaurantId, Long productId, String rel) {
        return linkTo(methodOn(RestaurantProductPhotoController.class)
                .find(restaurantId, productId)).withRel(rel);
    }

    public Link linkToPhotoProduct(Long restaurantId, Long productId) {
        return linkToPhotoProduct(restaurantId, productId, IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchens(String rel) {
        return linkTo(KitchenController.class).withRel(rel);
    }

    public Link linkToKitchens() {
        return linkToKitchens(IanaLinkRelations.SELF.value());
    }

    public Link linkToKitchen(Long kitchenId, String rel) {
        return linkTo(methodOn(KitchenController.class)
                .find(kitchenId)).withRel(rel);
    }

    public Link linkToKitchen(Long kitchenId) {
        return linkToKitchen(kitchenId, IanaLinkRelations.SELF.value());
    }

    public Link linkToStatistics(String rel) {
        return linkTo(StatisticsController.class).withRel(rel);
    }

    public Link linkToStatisticsSalesDaily(String rel) {
        TemplateVariables filterVariables = new TemplateVariables(
                new TemplateVariable("restaurantId", VariableType.REQUEST_PARAM),
                new TemplateVariable("createAt", VariableType.REQUEST_PARAM),
                new TemplateVariable("createEnd", VariableType.REQUEST_PARAM),
                new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));

        String demandsUrl = linkTo(methodOn(StatisticsController.class)
                .consultDailySales(null, null)).toUri().toString();

        return Link.of(UriTemplate.of(demandsUrl, filterVariables), rel);
    }

}
