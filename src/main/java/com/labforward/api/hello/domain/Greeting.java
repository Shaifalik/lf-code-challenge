package com.labforward.api.hello.domain;

import com.labforward.api.core.validation.Entity;
import com.labforward.api.core.validation.EntityUpdateValidatorGroup;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Simple greeting message for dev purposes
 */
@Getter
@Setter
public class Greeting implements Entity {

	@NotEmpty(groups = {EntityUpdateValidatorGroup.class})
	private String id;

	@NotEmpty
	private String message;

	public Greeting() {
		// needed for JSON deserialization
	}

	public Greeting(String id, String message) {
		this.message = message;
		this.id = id;
	}

	public Greeting(String message) {
		this.message = message;
	}

}
