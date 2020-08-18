package com.labforward.api.hello.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class GreetingDTO {

    private String id;

    @NotEmpty
    private String message;

    public GreetingDTO(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
