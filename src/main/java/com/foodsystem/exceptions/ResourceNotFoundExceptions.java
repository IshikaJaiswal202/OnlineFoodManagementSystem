package com.foodsystem.exceptions;

public class ResourceNotFoundExceptions extends RuntimeException{
    public ResourceNotFoundExceptions()
    {
        super();
    }
    public ResourceNotFoundExceptions(String msg)
    {
        super(msg);
    }
}
