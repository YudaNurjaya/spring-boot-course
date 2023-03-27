package com.example.demospringboot.utils.specification;

import com.example.demospringboot.utils.constans.Operator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class SearchCriteria {
    private String key;
    private Operator operation;
    private String value;

    public SearchCriteria(String key, Operator operation, String value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
