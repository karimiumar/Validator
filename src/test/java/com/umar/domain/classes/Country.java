package com.umar.domain.classes;

import com.umar.validator.annotations.ErrorMessage;
import com.umar.validator.annotations.IgnoreValidation;

public class Country {
    @IgnoreValidation
    private final int id;
    @ErrorMessage(errorMessage = "Country name is required")
    private final String name;
    @ErrorMessage(errorMessage = "Country code is required")
    private final String code;

    public Country(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString(){
        return String.format("Country {id:%d, name:%s, code:%s} ", id, name, code);
    }
}
