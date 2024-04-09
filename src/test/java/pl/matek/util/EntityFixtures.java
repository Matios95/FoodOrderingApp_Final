package pl.matek.util;

import lombok.experimental.UtilityClass;
import pl.matek.domain.*;
import pl.matek.infrastructure.database.entity.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@UtilityClass
public class EntityFixtures {

    public static CustomerRegister customerRegister1(){
        return CustomerRegister.builder()
                .name("AndrzejCustomer")
                .surname("NowakCustomer")
                .country("Poland")
                .postcode("21-123")
                .street("Street")
                .streetNumber(777)
                .email("andrzej.nowak@customer.pl")
                .password("55asd81413")
                .build();
    }

    public static OwnerRegister ownerRegister1(){
        return OwnerRegister.builder()
                .name("Andrzej")
                .surname("Nowak")
                .email("andrzej.nowak@zajavka.pl")
                .password("asdadssda1213113")
                .build();
    }

    public static Place place1() {
        return Place.builder()
                .placeId(1)
                .phone("+48 777 777 888")
                .name("Place name")
                .owner(owner1())
                .build();
    }

    public static Place place2() {
        return Place.builder()
                .placeId(2)
                .phone("+48 999 777 888")
                .name("Place name2")
                .owner(owner1())
                .build();
    }

    public static PlaceEntity placeEntity1() {
        return PlaceEntity.builder()
                .placeId(1)
                .phone("+48 777 777 888")
                .name("Place name")
                .owner(ownerEntity1())
                .build();
    }

    public static PlaceEntity placeEntity2() {
        return PlaceEntity.builder()
                .placeId(2)
                .phone("+48 999 777 888")
                .name("Place name2")
                .owner(ownerEntity1())
                .build();
    }

    public static Owner owner1() {
        return Owner.builder()
                .email("email")
                .name("name")
                .surname("surname")
                .build();
    }

    public static Owner owner2() {
        return Owner.builder()
                .email("email2")
                .name("name2")
                .surname("surname2")
                .build();
    }

    public static OwnerEntity ownerEntity1() {
        return OwnerEntity.builder()
                .email("email")
                .name("name")
                .surname("surname")
                .build();
    }

    public static OwnerEntity ownerEntity2() {
        return OwnerEntity.builder()
                .email("email2")
                .name("name2")
                .surname("surname2")
                .build();
    }

    public static ProductEntity productEntity1(){
        return ProductEntity.builder()
                .productCode("abvccbxS123")
                .type(ProductType.APPETIZER)
                .name("Salate")
                .description("This is your prefer product")
                .price(BigDecimal.TEN)
                .build();
    }

    public static Product product1(){
        return Product.builder()
                .productCode("abvccbxS123")
                .type(ProductType.APPETIZER)
                .name("Salate")
                .description("This is your prefer product")
                .price(BigDecimal.TEN)
                .build();
    }

    public static Order order1() {
        return Order.builder()
                .orderaCode("131131adsadsdas")
                .quantity(5)
                .foodOrderingRequestEntity(foodOrderingRequest1())
                .build();
    }

    public static Order order2() {
        return Order.builder()
                .orderaCode("aasdz131adsadsdas")
                .quantity(7)
                .foodOrderingRequestEntity(foodOrderingRequest1())
                .build();
    }

    public static OrderEntity orderEntity1() {
        return OrderEntity.builder()
                .orderaCode("131131adsadsdas")
                .quantity(5)
                .foodOrderingRequestEntity(foodOrderingRequestEntity1())
                .build();
    }

    public static OrderEntity orderEntity2() {
        return OrderEntity.builder()
                .orderaCode("aasdz131adsadsdas")
                .quantity(7)
                .foodOrderingRequestEntity(foodOrderingRequestEntity1())
                .build();
    }

    public static FoodOrderingRequestEntity foodOrderingRequestEntity2() {
        return FoodOrderingRequestEntity.builder()
                .foodOrderingRequestId(2)
                .foodOrderingRequestCode("1121zzcxzcx3121adsadsads")
                .datetime(OffsetDateTime.now())
                .completed(false)
                .build();
    }

    public static FoodOrderingRequestEntity foodOrderingRequestEntity1() {
        return FoodOrderingRequestEntity.builder()
                .foodOrderingRequestId(1)
                .foodOrderingRequestCode("123133121adsadsads")
                .datetime(OffsetDateTime.now())
                .completed(false)
                .build();
    }

    public static FoodOrderingRequest foodOrderingRequest2() {
        return FoodOrderingRequest.builder()
                .foodOrderingRequestId(2)
                .foodOrderingRequestCode("1121zzcxzcx3121adsadsads")
                .datetime(OffsetDateTime.now())
                .completed(false)
                .build();
    }

    public static FoodOrderingRequest foodOrderingRequest1() {
        return FoodOrderingRequest.builder()
                .foodOrderingRequestId(1)
                .foodOrderingRequestCode("123133121adsadsads")
                .datetime(OffsetDateTime.now())
                .completed(false)
                .build();
    }

    public static DeliveryAddressEntity deliveryAddressEntity2() {
        return DeliveryAddressEntity.builder()
                .deliveryAddressId(2)
                .postcode("88-111")
                .street("Long")
                .build();
    }

    public static DeliveryAddress deliveryAddress2() {
        return DeliveryAddress.builder()
                .deliveryAddressId(2)
                .postcode("88-111")
                .street("Long")
                .build();
    }

    public static DeliveryAddressEntity deliveryAddressEntity1() {
        return DeliveryAddressEntity.builder()
                .deliveryAddressId(1)
                .postcode("99-111")
                .street("Street")
                .build();
    }

    public static DeliveryAddress deliveryAddress1() {
        return DeliveryAddress.builder()
                .deliveryAddressId(1)
                .postcode("99-111")
                .street("Street")
                .build();
    }

    public static Customer customer1() {
        return Customer.builder()
                .email("email@gmail.com")
                .name("Stefanek")
                .surname("Nowak")
                .build();
    }

    public static CustomerEntity customerEntity1() {
        return CustomerEntity.builder()
                .email("email@gmail.com")
                .name("Stefanek")
                .surname("Nowak")
                .build();
    }

    public static Address address1() {
        return Address.builder()
                .country("Poland")
                .postcode("00-999")
                .street("Street")
                .streetNumber(777)
                .build();
    }

    public static AddressEntity addressEntity1() {
        return AddressEntity.builder()
                .country("Poland")
                .postcode("00-999")
                .street("Street")
                .streetNumber(777)
                .build();
    }

    public static AddressEntity addressEntity2() {
        return AddressEntity.builder()
                .country("Poland")
                .postcode("00-999")
                .street("Street")
                .streetNumber(888)
                .build();
    }

}
