package com.example.inventory.aggregator;

import lombok.Data;

@Data
public class Result {

    private final boolean result;
    private final String info;

    private Result(boolean result, String info) {
        this.result = result;
        this.info = info;
    }

    public static Result ok() {
        return new Result(true, "Command applied");
    }

    public static Result failure(String message) {
        return new Result(false, message);
    }

}