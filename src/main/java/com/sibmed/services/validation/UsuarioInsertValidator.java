package com.sibmed.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.sibmed.domain.Usuario;
import com.sibmed.domain.dto.UsuarioNewDTO;
import com.sibmed.repositories.UsuarioRepository;
import com.sibmed.resources.exception.FieldMessage;

public class UsuarioInsertValidator implements ConstraintValidator<UsuarioInsert, UsuarioNewDTO> {
		
	
	@Autowired
	private UsuarioRepository repo;
	
	@Override
	public void initialize(UsuarioInsert ann) {
	}
	 	
		@Override
		public boolean isValid(UsuarioNewDTO objDto, ConstraintValidatorContext context) {
			List<FieldMessage> list = new ArrayList<>();
			
			Usuario aux = repo.findByEmail(objDto.getEmail());
			if(aux !=null) {
				list.add(new FieldMessage("email", "Email já existente"));
			}
			
			for (FieldMessage e : list) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
						.addConstraintViolation();
			}
			return list.isEmpty();
	}

}
