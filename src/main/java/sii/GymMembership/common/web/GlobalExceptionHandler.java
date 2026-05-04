package sii.GymMembership.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sii.GymMembership.common.exception.DuplicateGymNameException;
import sii.GymMembership.common.exception.DuplicateMemberEmailException;
import sii.GymMembership.common.exception.DuplicateMembershipPlanNameException;
import sii.GymMembership.common.exception.GymNotFoundException;
import sii.GymMembership.common.exception.MemberNotFoundException;
import sii.GymMembership.common.exception.InvalidCurrencyException;
import sii.GymMembership.common.exception.MembershipPlanNotFoundException;
import sii.GymMembership.common.exception.PlanAtCapacityException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DuplicateGymNameException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateGymNameException(
			DuplicateGymNameException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Conflict");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleMemberNotFoundException(
			MemberNotFoundException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateMemberEmailException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateMemberEmailException(
			DuplicateMemberEmailException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Conflict");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(GymNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleGymNotFoundException(
			GymNotFoundException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateMembershipPlanNameException.class)
	public ResponseEntity<Map<String, Object>> handleDuplicateMembershipPlanNameException(
			DuplicateMembershipPlanNameException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Conflict");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(InvalidCurrencyException.class)
	public ResponseEntity<Map<String, Object>> handleInvalidCurrencyException(
			InvalidCurrencyException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad Request");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MembershipPlanNotFoundException.class)
	public ResponseEntity<Map<String, Object>> handleMembershipPlanNotFoundException(
			MembershipPlanNotFoundException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.NOT_FOUND.value());
		body.put("error", "Not Found");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PlanAtCapacityException.class)
	public ResponseEntity<Map<String, Object>> handlePlanAtCapacityException(
			PlanAtCapacityException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.CONFLICT.value());
		body.put("error", "Conflict");
		body.put("message", ex.getMessage());
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Bad Request");
		Throwable cause = ex.getMostSpecificCause();
		String detail = cause.getMessage() != null
			? cause.getMessage()
			: ex.getMessage();
		body.put("message", detail != null ? detail : "Malformed JSON or invalid value in request body");
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationException(
			MethodArgumentNotValidException ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("error", "Validation Error");
		String validationMessage = "Invalid request body";
		var firstError = ex.getBindingResult().getAllErrors().stream().findFirst().orElse(null);
		if (firstError != null && firstError.getDefaultMessage() != null) {
			validationMessage = firstError.getDefaultMessage();
		}
		body.put("message", validationMessage);
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleGlobalException(
			Exception ex,
			WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
		body.put("error", "Internal Server Error");
		body.put("message", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred");
		body.put("path", request.getDescription(false).replace("uri=", ""));
		return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

