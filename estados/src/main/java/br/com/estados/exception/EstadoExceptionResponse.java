package br.com.estados.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EstadoExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;
}
