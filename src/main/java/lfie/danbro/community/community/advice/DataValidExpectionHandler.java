//package lfie.danbro.community.community.advice;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindException;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@ControllerAdvice
//public class DataValidExpectionHandler extends ResponseEntityExceptionHandler {
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        Map<String, String> errorMap = new HashMap<>();
//        BindingResult result = ex.getBindingResult();
//        if (result.hasErrors()) {
//            List<ObjectError> errors = result.getAllErrors();
//            for (ObjectError error : errors) {
//                FieldError fieldError = (FieldError) error;
//                errorMap.put(fieldError.getField(), error.getDefaultMessage());
//            }
//            logger.error(errorMap.toString());
//        }
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
//    }
//}
