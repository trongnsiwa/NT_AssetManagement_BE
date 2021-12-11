package com.nashtech.rootkies.dto.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO {

    private String errorCode;

    private Object data;

    private String successCode;

}
