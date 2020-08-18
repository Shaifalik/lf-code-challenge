package com.labforward.api.hello.service;

import com.labforward.api.core.exception.BadRequestException;
import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.core.validation.EntityValidator;
import com.labforward.api.hello.ApplicationConstants;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.repo.HelloWorldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class HelloWorldService {
    @Autowired
    private HelloWorldRepository helloWorldRepository;

    private EntityValidator entityValidator;

    public HelloWorldService(EntityValidator entityValidator) {
        this.entityValidator = entityValidator;
    }

    public Greeting createGreeting(Greeting request) {
        entityValidator.validateCreate(request);
        Optional<Greeting> opGreeting = helloWorldRepository.findGreeting(request.getId());
        if (opGreeting.isPresent()) {
            throw new BadRequestException(ApplicationConstants.ID_ALREADY_EXIST);
        }
        if (request.getId()== null || request.getId().isEmpty())
            request.setId(UUID.randomUUID().toString());

        return helloWorldRepository.saveGreeting(request);
    }

    public Greeting getGreeting(String id) {
        entityValidator.validateId(id);
        return helloWorldRepository.findGreeting(id).
                orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.GREETING_NOT_FOUND + " " + id));
    }

    public Greeting getDefaultGreeting() {
        return getGreeting(ApplicationConstants.DEFAULT_ID);
    }

    public Greeting updateGreeting(String id, Greeting request) {
        entityValidator.validateUpdate(id, request);
        helloWorldRepository.findGreeting(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApplicationConstants.GREETING_NOT_FOUND + " " + id));
        return helloWorldRepository.saveGreeting(request);
    }
}
