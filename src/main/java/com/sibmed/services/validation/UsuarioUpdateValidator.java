package com.sibmed.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.sibmed.domain.Usuario;
import com.sibmed.domain.dto.UsuarioDTO;
import com.sibmed.repositories.UsuarioRepository;
import com.sibmed.resources.exception.FieldMessage;

public class UsuarioUpdateValidator implements ConstraintValidator<UsuarioUpdate, UsuarioDTO> {

 	@Autowired
	private HttpServletRequest request;

 	@Autowired
	private UsuarioRepository repo;

 	@Override
	public void initialize(UsuarioUpdate ann) {
	}

 	@Override
	public boolean isValid(UsuarioDTO objDto, ConstraintValidatorContext context) {

 		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

 		List<FieldMessage> list = new ArrayList<>();

 		Usuario aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {
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
