package com.geobyte.lcmsbe.util;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This is middleware component Spring container invokes whenever error is generated in the application
 * @ControllerAdvice rigs it up as container managed component
 * 
 * Returns to Rest Client an impregnated http response called ResponseEntity
 */


@ControllerAdvice
public class ApplicationExceptionHandler {
	
		// Spring container invokes this handler whenever RoutesTableSetupException is thrown from anywhere
		// in the application
		@ExceptionHandler
		public ResponseEntity<ErrorStatusMessage> handleException(RoutesTableSetupException exc) {
			ErrorStatusMessage error = new ErrorStatusMessage();
			
			error.setStatusCode(HttpStatus.NOT_FOUND.value());
			error.setMessage(exc.getMessage());
			error.setStatusInWord(StatusInWord.FAILED);
			
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
	
		// Spring container invokes this handler whenever EntityNotFoundException is thrown from anywhere
		// in the application
		@ExceptionHandler
		public ResponseEntity<ErrorStatusMessage> handleException(EntityNotFoundException exc) {
			ErrorStatusMessage error = new ErrorStatusMessage();
			
			error.setStatusCode(HttpStatus.NOT_FOUND.value());
			error.setMessage(exc.getMessage());
			error.setStatusInWord(StatusInWord.FAILED);
			
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		// Spring container invokes this handler whenever EntityNotUpdatedException is thrown from anywhere
		// in the application
		@ExceptionHandler
		public ResponseEntity<ErrorStatusMessage> handleException(EntityNotUpdatedException exc) {
			ErrorStatusMessage error = new ErrorStatusMessage();
			
			error.setStatusCode(HttpStatus.NOT_FOUND.value());
			error.setMessage(exc.getMessage());
			error.setStatusInWord(StatusInWord.FAILED);
			
			return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
		}
		
		// Spring container invokes this handler for any exception (except for the above) thrown from within the application
		@ExceptionHandler
		public ResponseEntity<ErrorStatusMessage> handleException(Exception exc) {
			ErrorStatusMessage error = new ErrorStatusMessage();
			
			error.setStatusCode(HttpStatus.BAD_REQUEST.value());
			error.setMessage("Bad path variable, DB server down or something else went wrong. Consider restarting both application and db servers of error persist");
			error.setStatusInWord(StatusInWord.FAILED);
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
}