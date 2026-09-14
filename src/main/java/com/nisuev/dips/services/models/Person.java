package com.nisuev.dips.services.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Person {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String work;
}
