package br.com.compasso.cepms.resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.compasso.cepms.exception.TechnicalException;
import br.com.compasso.cepms.resource.dto.ErrorDTO;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

	@ExceptionHandler({ TechnicalException.class })
	public ResponseEntity<ErrorDTO> handleTechnicalException(TechnicalException ex) {
		ErrorDTO error = new ErrorDTO();
		error.setCode("-1");
		error.setMessage("Erro técnico: " + ex.getMessage());
		error.setDetail(ExceptionUtils.getStackTrace(ex));

		return new ResponseEntity<ErrorDTO>(error, HttpStatus.BAD_REQUEST);
	}
}
