package com.foodsystem.builder;

import org.springframework.http.HttpStatus;

public class ApiResponse {

    private String msg;
    private HttpStatus code;
    private Boolean success;

    // Constructor
    public ApiResponse(Builder builder) {
        this.msg = builder.msg;
        this.code = builder.code;
        this.success = builder.success;
    }

    public ApiResponse(String foodCartAddedSuccessfully, HttpStatus httpStatus, boolean b) {
    }

    // Getters and Setters
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    // Static Builder Class
    public static class Builder {
        private String msg;
        private HttpStatus code;
        private Boolean success;

        public Builder() {
        }

        public Builder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder code(HttpStatus code) {
            this.code = code;
            return this;
        }

        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }

        // Method to build the ApiResponse object
        public ApiResponse build() {
            return new ApiResponse(this);
        }
    }

    // You can use this builder in your GlobalExceptionHandler as follows:
    // return new ApiResponse.Builder().msg(e.getMessage()).code(HttpStatus.NOT_FOUND).success(false).build();
}
