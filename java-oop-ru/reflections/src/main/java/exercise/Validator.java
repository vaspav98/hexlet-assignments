package exercise;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// BEGIN
public class Validator {

    public static List<String> validate(Object object) {
        List<String> result = new ArrayList<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            Object valueOfField = null;
            try {
                field.setAccessible(true);
                valueOfField = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (field.isAnnotationPresent(NotNull.class)) {
                if (valueOfField == null) {
                    result.add(field.getName());
                }
            }
        }
        return result;
    }

    public static Map<String, List<String>> advancedValidate(Object object) {
        Map<String, List<String>> result = new HashMap<>();

        for (Field field : object.getClass().getDeclaredFields()) {
            Object valueOfField = null;
            try {
                field.setAccessible(true);
                valueOfField = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            if (field.isAnnotationPresent(NotNull.class)) {
                List<String> description = Arrays.asList("can not be null".split(" "));
                if (valueOfField == null) {
                    result.put(field.getName(), description);
                }
            }

            if (field.isAnnotationPresent(MinLength.class)) {
                MinLength min = field.getAnnotation(MinLength.class);
                List<String> description = Arrays.asList(("length less than " + min.minLength()).split(" "));
                if (valueOfField != null && valueOfField.toString().length() < min.minLength()) {
                    result.put(field.getName(), description);
                }
            }

        }
        return result;
    }
}
// END
