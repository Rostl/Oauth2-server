package com.example.myserver.oauthserverpostgres.converter;

@FunctionalInterface
public interface Converter<T, R> {

    R convert(T Object);
}