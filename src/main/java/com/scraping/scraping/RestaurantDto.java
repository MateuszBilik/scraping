package com.scraping.scraping;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDto {

    private String name;
    private String description;
    private String timeToDelivery;
    private String workingHours;
    private String address;
    private String web;

    @Override
    public String toString() {
        return  "\nname='" + name + '\'' +
                ", description='" + description + '\'' +
                ", timeToDelivery='" + timeToDelivery + '\'' +
                ", workingHours='" + workingHours + '\'' +
                ", address='" + address + '\'' +
                ", web='" + web + '\'' +
                '}';
    }
}
