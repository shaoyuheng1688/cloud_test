package com.raymon.taxguide.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Builder<T> {
    private final Supplier<T> instantiator;
    private List<Consumer<T>> setterList = new ArrayList<>();

    private Builder(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> Builder<T> of(Supplier<T> instantiator) {
        return new Builder<>(instantiator);
    }

    public <P> Builder<T> with(IOneParamSetter<T, P> setter, P p) {
        Consumer<T> c = instance -> setter.accept(instance, p);
        setterList.add(c);
        return this;
    }

    public <P0, P1> Builder<T> with(ITwoParamSetter<T, P0, P1> setter, P0 p0, P1 p1) {
        Consumer<T> c = instance -> setter.accept(instance, p0, p1);
        setterList.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        setterList.forEach(setter -> setter.accept(value));
        setterList.clear();
        return value;
    }

    @FunctionalInterface
    public interface IOneParamSetter<T, P> {
        void accept(T t, P p);
    }

    @FunctionalInterface
    public interface ITwoParamSetter<T, P0, P1> {
        void accept(T t, P0 p0, P1 p1);
    }

}