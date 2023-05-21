package ru.clevertec.ecl.utils;

import ru.clevertec.ecl.model.dto.GiftCertificateDto;
import ru.clevertec.ecl.model.dto.OrderDto;
import ru.clevertec.ecl.model.dto.TagDto;
import ru.clevertec.ecl.model.dto.UserDto;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.Order;
import ru.clevertec.ecl.model.entity.Tag;
import ru.clevertec.ecl.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TestData {

    private static final LocalDateTime YESTERDAY = LocalDateTime.now().minusDays(1);

    public static UserDto buildUserDto() {
        return UserDto.builder()
                .id(1L)
                .name("user")
                .build();
    }

    public static User buildUser() {
        return User.builder()
                .id(1L)
                .name("user")
                .build();
    }

    public static TagDto buildTagDto() {
        return TagDto.builder()
                .id(1L)
                .name("spring")
                .build();
    }

    public static Tag buildTag() {
        return Tag.builder()
                .id(1L)
                .name("spring")
                .build();
    }

    public static GiftCertificateDto buildGiftCertificateDtoWithoutTags() {
        return GiftCertificateDto.builder()
                .id(1L)
                .name("valid certificate")
                .description("with all fields except tags")
                .price(BigDecimal.valueOf(10))
                .duration(7)
                .build();
    }

    public static GiftCertificateDto buildGiftCertificateDtoWithTags() {
        return GiftCertificateDto.builder()
                .id(1L)
                .name("valid certificate")
                .description("with all fields except tags")
                .price(BigDecimal.valueOf(10))
                .duration(7)
                .tags(List.of(TestData.buildTagDto()))
                .build();
    }

    public static GiftCertificate buildGiftCertificateWithoutTags() {

        return GiftCertificate.builder()
                .id(1L)
                .name("valid certificate")
                .description("with all fields except tags")
                .price(BigDecimal.valueOf(10))
                .duration(7)
                .createDate(YESTERDAY)
                .lastUpdateDate(YESTERDAY)
                .build();
    }

    public static GiftCertificate buildGiftCertificateWithTags() {
        return GiftCertificate.builder()
                .id(1L)
                .name("valid certificate")
                .description("with all fields except tags")
                .price(BigDecimal.valueOf(10))
                .duration(7)
                .createDate(YESTERDAY)
                .lastUpdateDate(YESTERDAY)
                .tags(List.of(TestData.buildTag()))
                .build();
    }

    public static OrderDto buildOrderDto() {
        return OrderDto.builder()
                .id(1L)
                .price(BigDecimal.valueOf(10))
                .orderDate(YESTERDAY)
                .build();
    }

    public static Order buildOrder() {
        return Order.builder()
                .id(1L)
                .price(BigDecimal.valueOf(10))
                .orderDate(YESTERDAY)
                .build();
    }
}
