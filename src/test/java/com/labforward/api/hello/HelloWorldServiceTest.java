package com.labforward.api.hello;

import com.labforward.api.Application;
import com.labforward.api.core.exception.BadRequestException;
import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.labforward.api.hello.ApplicationConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {

    @Autowired
    private HelloWorldService helloService;

    private final static String HELLO_LUKE = "Hello Luke";

    private final static String NEW_MESSAGE = "New Message";

    private final String EMPTY_MESSAGE = "";

    private final String INVALID_ID = "1";
    private final String VALID_ID = "10";
    private final String VALID_IDS = "2";


    public HelloWorldServiceTest() {
    }

    @Test
    public void getDefaultGreetingIsOK() {
        Greeting greeting = helloService.getDefaultGreeting();
        Assert.assertEquals(DEFAULT_ID, greeting.getId());
        Assert.assertEquals(DEFAULT_MESSAGE, greeting.getMessage());
    }

    @Test(expected = EntityValidationException.class)
    public void getGreetingFailedWithNullId() {
        helloService.getGreeting(null);
        ;
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getGreetingFailedWithInvalidId() {
        helloService.getGreeting(INVALID_ID);
    }

    public void getGreetingIsOkWithValidId() {
        Greeting greeting = helloService.createGreeting(new Greeting(NEW_MESSAGE));
        Greeting data = helloService.getGreeting(greeting.getId());
        Assert.assertEquals(data.getMessage(), NEW_MESSAGE);
    }

    @Test(expected = EntityValidationException.class)
    public void createGreetingWithEmptyMessageThrowsException() {
        helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
    }

    @Test(expected = EntityValidationException.class)
    public void createGreetingWithNullMessageThrowsException() {
        helloService.createGreeting(new Greeting(null));
    }

    @Test
    public void createGreetingOKWhenValidRequestMessage() {
        Greeting request = new Greeting(HELLO_LUKE);

        Greeting created = helloService.createGreeting(request);
        Assert.assertEquals(HELLO_LUKE, created.getMessage());
        Assert.assertNotNull(created.getId());
    }

    @Test
    public void createGreetingOKWhenValidRequest() {
        Greeting request = new Greeting(VALID_IDS, HELLO_LUKE);

        Greeting created = helloService.createGreeting(request);
        Assert.assertEquals(VALID_IDS, created.getId());
    }

    @Test(expected = BadRequestException.class)
    public void createGreetingFailedWithExistingRequestId() {
        Greeting request = new Greeting(VALID_ID, HELLO_LUKE);
        helloService.createGreeting(request);

        Assert.assertEquals(VALID_ID, request.getId());
        helloService.createGreeting(request);
    }

    @Test(expected = EntityValidationException.class)
    public void updateGreetingFailedWhenNullRequestId() {
        Greeting updateReq = new Greeting(INVALID_ID, NEW_MESSAGE);
        helloService.updateGreeting(null, updateReq);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateGreetingFailedWhenInValidRequestId() {
        Greeting updateReq = new Greeting(VALID_ID, NEW_MESSAGE);
        helloService.updateGreeting(VALID_ID, updateReq);
    }

    @Test(expected = EntityValidationException.class)
    public void updateGreetingFailedWhenRequestIdMismatchesDetails() {
        Greeting updateReq = new Greeting(VALID_ID, NEW_MESSAGE);
        helloService.updateGreeting(INVALID_ID, updateReq);
    }

    @Test
    public void updateExistingGreetingOKWhenValidRequest() {
        Greeting request = helloService.createGreeting(new Greeting(HELLO_LUKE));
        Greeting updateReq = new Greeting(request.getId(),NEW_MESSAGE);
        Greeting created = helloService.updateGreeting(request.getId(),updateReq);
        Assert.assertEquals(NEW_MESSAGE, created.getMessage());
    }
}
