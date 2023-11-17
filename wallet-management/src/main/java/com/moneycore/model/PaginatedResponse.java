package com.moneycore.model;

import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class PaginatedResponse {

    boolean status;
    Integer statusCode;
    String message;
//    Object data;
//    private Integer pageNo;
//    private Integer pageSize;
//    private Integer totalPages;

    // default initialize
    Map<String, Object> data = new LinkedHashMap<String, Object>(){{
        put("pageNo", "1");
        put("pageSize", "10");
        put("totalPages", null);
        put("totalElements", null);
        put("list", null);
    }};


    public PaginatedResponse() {
    }

    public PaginatedResponse(boolean status, Integer statusCode, String message, Object data, Integer pageNo, Integer pageSize, Integer totalPages, Long totalElements) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.data.put("pageNo", pageNo);
        this.data.put("pageSize", pageSize);
        this.data.put("totalPages", totalPages);
        this.data.put("totalElements", totalElements);
        this.data.put("list",data );
    }

}
