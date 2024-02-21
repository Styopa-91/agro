package com.abi.agro_back.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    NAME("name"),
    START_DATE("startDate");

    private final String databaseFieldName;
}
