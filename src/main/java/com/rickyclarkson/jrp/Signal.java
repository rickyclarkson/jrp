package com.rickyclarkson.jrp;

public interface Signal<T> {
    T apply();
    void addDependent(Signal<?> dependent);
    T now();
}
