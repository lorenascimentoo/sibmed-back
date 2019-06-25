package com.sibmed.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sibmed.domain.Bula;
import com.sibmed.repositories.BulaRepository;
import com.sibmed.resources.exception.FieldMessage;

public class BulaInsertValidator implements ConstraintValidator<BulaInsert, Bula> {

	@Autowired
	private BulaRepository repo;

	@Override
	public void initialize(BulaInsert ann) {
	}

	@Override
	public boolean isValid(Bula obj, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		Optional<Bula> aux = repo.findByDir(obj.getDir());
		if (aux != null) {
			list.add(new FieldMessage("arquivo", "Bula já existente"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();
	}

}
