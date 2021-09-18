package io.company.brewcraft.util;

import java.util.Arrays;

/***
 * Custom Collection Utils class for methods not available in external
 * libraries. Util classes and methods are sign of code-smell. Keep the class
 * small and minimal. Think thrice before adding a function in a util class.
 * Before adding a method as a utility, ask yourself, can I add this util
 * function as a "public" method to an existing? If not, does it make sense to
 * create a new class that would expose this method as a public class?
 *
 */

public abstract class CollectionUtils {
    public static <T> T[] addToArray(T[] source, T... extra) {
        int lenOriginal = source.length;
        source = Arrays.copyOf(source, source.length + extra.length);

        for (int i = 0; i < extra.length; i++) {
            source[lenOriginal + i] = extra[i];
        }

        return source;
    }
}
