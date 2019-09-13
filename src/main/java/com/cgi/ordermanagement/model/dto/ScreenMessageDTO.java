package com.cgi.ordermanagement.model.dto;

import com.cgi.ordermanagement.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenMessageDTO {

    private Long id;
    private String code;
    private String value;
    private Language language;

    public ScreenMessageDTO(String value){
        this.value = value;
    }

}
