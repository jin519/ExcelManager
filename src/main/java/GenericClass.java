import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 원진
 * @contact godjin519@gmail.com
 */
public class GenericClass
{
    /**
     * 타입 인스턴스를 반환한다.
     * @param typeClass 타입 클래스 정보
     * @param <T> 타입
     * @return 타입 인스턴스
     */
    public static <T> T getInstance(final Class<T> typeClass) throws IllegalAccessException, InstantiationException
    {
        return typeClass.newInstance();
    }

    /**
     * 타입 클래스 내 선언된 필드 목록을 반환한다.
     * @param typeClass 타입 클래스 정보
     * @param <T> 타입
     * @return 타입 클래스 내 선언된 필드 목록
     */
    public static <T> List<Field> getDeclaredFields(final Class<T> typeClass)
    {
        return  Arrays
                .stream(typeClass.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }
}