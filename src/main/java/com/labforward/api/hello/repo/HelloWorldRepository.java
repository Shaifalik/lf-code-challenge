package com.labforward.api.hello.repo;

import com.labforward.api.hello.ApplicationConstants;
import com.labforward.api.hello.domain.Greeting;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class HelloWorldRepository {

    private static Map<String, Greeting> greetings;

    public HelloWorldRepository() {
        this.greetings = new HashMap<>(ApplicationConstants.MAP_INITIAL_CAPACITY);
        saveGreeting(new Greeting(ApplicationConstants.DEFAULT_ID, ApplicationConstants.DEFAULT_MESSAGE));
    }

    public Optional<Greeting> findGreeting(String greetingId) {
        Optional<Greeting> optionalGreeting = Optional.ofNullable(this.greetings.get(greetingId));
        return optionalGreeting;
    }

    public Greeting saveGreeting(Greeting greeting) {
        this.greetings.put(greeting.getId(), greeting);
        return greeting;
    }
}
