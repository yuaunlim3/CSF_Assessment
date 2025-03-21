package vttp.batch5.csf.assessment.server.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.json.Json;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vttp.batch5.csf.assessment.server.Model.Exception.FailedException;
import vttp.batch5.csf.assessment.server.Model.Exception.OrderFailedException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(FailedException.class)
    public ResponseEntity<String> handleAllOtherErrors(FailedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Json.createObjectBuilder().add("message", exception.getMessage()).build().toString());
    }

    @ExceptionHandler(OrderFailedException.class)
    public ResponseEntity<String> handleException(OrderFailedException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Json.createObjectBuilder().add("message", exception.getMessage()).build().toString());
    }
}
