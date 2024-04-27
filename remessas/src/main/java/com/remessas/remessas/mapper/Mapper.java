package com.remessas.remessas.mapper;

public interface Mapper<S, T> {
    T map(S objeto);
}
