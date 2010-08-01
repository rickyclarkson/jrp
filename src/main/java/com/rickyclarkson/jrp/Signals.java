package com.rickyclarkson.jrp;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public final class Signals {
    public static final Stack<List<Signal<?>>> stack = new Stack<List<Signal<?>>>();

    public static <T> Signal<T> signal(final ByName<T> byName) {
        return new Signal<T>() {
            final List<Signal<?>> dependents = new ArrayList<Signal<?>>();

            T now;

            {
                stack.push(new ArrayList<Signal<?>>());
                apply();
                for (Signal<?> dependencies: stack.pop())
                    dependencies.addDependent(this);
            }

            public T apply() {
                now = byName.apply();
                for (final Signal<?> dependent: dependents)
                    dependent.apply();
                try {
                    return now;
                } finally {
                    now = null;
                }
            }

            public void addDependent(Signal<?> dependent) {
                dependents.add(dependent);
            }

            public T now() {
                return now == null ? byName.apply() : now;
            }
        };
    }

    public static <T> void observe(final Signal<T> signal, final Effect<T> effect) {
        signal.addDependent(new Signal<Void>() {
            public Void apply() {
                effect.apply(signal.now());
                return null;
            }

            public void addDependent(Signal<?> dependent) {
                throw null;
            }

            public Void now() {
                return null;
            }
        });
    }
}
