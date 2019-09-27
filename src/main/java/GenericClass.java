import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericClass
{
    public static <T> T getInstance(final Class<T> typeClass)
    {
        T instance = null;

        try
        {
            instance = typeClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return instance;
    }

    public static <T> List<Field> getDeclaredFields(final Class<T> typeClass)
    {
        return  Arrays
                .stream(typeClass.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }
}