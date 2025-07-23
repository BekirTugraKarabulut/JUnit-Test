package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage<T> {

    private MessageType messageType;
    private T ofStatic;

    public String prepareErrorMessage(){

        StringBuilder builder = new StringBuilder();
        builder.append(messageType.getMessage());
        if(ofStatic != null){
            builder.append(" Hata Kaynağı : " + ofStatic);
        }
        return builder.toString();
    }

}
