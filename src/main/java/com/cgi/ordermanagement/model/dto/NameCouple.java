package com.cgi.ordermanagement.model.dto;

import com.cgi.ordermanagement.model.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NameCouple implements Serializable {
    private String name;
    private Language language;
}
