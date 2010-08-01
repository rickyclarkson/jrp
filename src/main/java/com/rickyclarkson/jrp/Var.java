package com.rickyclarkson.jrp;

import java.util.List;
import java.util.ArrayList;
import java.util.EmptyStackException;

public final class Var<T> implements Signal<T> {
    private T t;

    private final List<Signal<?>> dependents = new ArrayList<Signal<?>>();

    public Var(T t) {
        this.t = t;
    }

    public T apply() {
        try {
            Signals.stack.peek().add(this);
        } catch (EmptyStackException ignored) {
        }
        return t;
    }

    public void update(T t) {
        this.t = t;
        for (final Signal<?> dependent: dependents)
            dependent.apply();
    }

    public void addDependent(Signal<?> dependent) {
        dependents.add(dependent);
    }

    public T now() {
        return t;
    }
}
