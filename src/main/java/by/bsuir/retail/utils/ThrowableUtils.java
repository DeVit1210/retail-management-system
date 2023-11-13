package by.bsuir.retail.utils;

import lombok.AllArgsConstructor;

import java.util.function.Predicate;

@AllArgsConstructor
public class ThrowableUtils<T> {
    private T object;
    private Predicate<T> predicate;

    public static <T> ThrowableUtils<T> prepareTest(T object, Predicate<T> predicate) {
        return new ThrowableUtils<>(object, predicate);
    }

    public void orElseThrow(RuntimeException e) {
        boolean test = predicate.test(object);
        if(!test) {
            throw e;
        }
    }

    public void orElseThrow(Class<? extends RuntimeException> e) {
        boolean test = predicate.test(object);
        if(!test) {
            try {
                throw e.getDeclaredConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
