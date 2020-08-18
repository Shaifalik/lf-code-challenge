package com.labforward.api.core.validation;

import com.google.common.base.Preconditions;
import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.hello.domain.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

public class EntityValidator {

	public static final String MESSAGE_NO_ID_MATCH = "id provided does not match resource";
	public static final String ID_NOT_PRESENT = "id is null or empty";

	private SpringValidatorAdapter validatorAdapter;

	@Autowired //Is this required
	public EntityValidator(SpringValidatorAdapter validatorAdapter) {
		this.validatorAdapter = validatorAdapter;
	}

	public void validateCreate(Object target, Object... groups) throws EntityValidationException {
		validate(target, groups);
	}

	public void validateId(String id) throws EntityValidationException {
		Greeting target = new Greeting();
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());
		checkPreConditions(id,target,result);
	}

	public void validateUpdate(String id, Entity target) throws EntityValidationException {
		Preconditions.checkArgument(target != null);
		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());
		checkPreConditions(id,target,result);

		// validate ids match
		if (!id.equals(target.getId())) {
			FieldError fieldError = new FieldError(target.getClass().getName(), "id", MESSAGE_NO_ID_MATCH);
			result.addError(fieldError);
			throw new EntityValidationException(result);
		}

		validatorAdapter.validate(target, result, EntityUpdateValidatorGroup.class);

		if (result.hasErrors()) {
			throw new EntityValidationException(result);
		}
	}

	protected void validate(Object target, Object... groups) throws EntityValidationException {
		Preconditions.checkArgument(target != null);

		BeanPropertyBindingResult result = new BeanPropertyBindingResult(target, target.getClass().getName());
		validatorAdapter.validate(target, result, groups);

		if (result.hasErrors()) {
			throw new EntityValidationException(result);
		}
	}

	public void checkPreConditions(String id, Entity target, BeanPropertyBindingResult result) throws EntityValidationException {
		try {
			Preconditions.checkArgument(id != null);
			Preconditions.checkArgument(!id.trim().isEmpty());
		} catch (IllegalArgumentException ex) {
			FieldError fieldError = new FieldError(target.getClass().getName(), "id", ID_NOT_PRESENT);
			result.addError(fieldError);
			throw new EntityValidationException(result);
		}
	}
}
