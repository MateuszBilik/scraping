package com.scraping.scraping;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RestaurantDto {

    private String name;
    private String description;
    private String timeToDelivery;
    private String workingHours;
    private String address;
    private String web;
}
