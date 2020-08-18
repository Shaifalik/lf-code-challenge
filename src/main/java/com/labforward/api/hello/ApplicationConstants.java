package com.labforward.api.hello;

import com.labforward.api.core.domain.ApiMessage;

public class ApplicationConstants {

    public static final String DEFAULT_ID = "Default";
    public static final String DEFAULT_MESSAGE = "Hello World!";
    public static final Integer MAP_INITIAL_CAPACITY = 1;

    public static final String GREETING_NOT_FOUND = "Greeting Not Found";
    public static final String ID_ALREADY_EXIST = "Greeting Id already present";
    public static final String MESSAGE_UNRECOGNIZED_PROPERTY = "Unrecognized property: ";
    public static final String MESSAGE_BAD_REQUEST = "Client error: server will not process request";
    public static final ApiMessage GENERIC_NOT_FOUND_MESSAGE = new ApiMessage("Entity not found.");
}
