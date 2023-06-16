package com.example.blog.base.exception;

public class BlogException extends Exception {
    private CodeAndMsg exception;

    public BlogException(CodeAndMsg exception) {
        this.exception = exception;
    }

    public CodeAndMsg getException() {
        return exception;
    }

    public void setException(CodeAndMsg exception) {
        this.exception = exception;
    }
}
