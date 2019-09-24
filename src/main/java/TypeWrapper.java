import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 제네릭 타입 래핑 클래스
 * @param <T> 타입 파라미터
 * @author 원진
 * @contact godjin@gmail.com
 */
public class TypeWrapper<T>
{
    /**
     * 타입 클래스
     */
    private Class<T> typeClass;

    /**
     * 타입 클래스 내 선언된 필드 리스트
     */
    private List<Field> declaredFields;

    /**
     * 생성자
     * @param typeClass 타입 클래스
     */
    public TypeWrapper(final Class<T> typeClass)
    {
        setTypeClass(typeClass);
    }

    /**
     * 타입 클래스를 설정한다.
     * @param typeClass 타입 클래스
     */
    public void setTypeClass(final Class<T> typeClass)
    {
        this.typeClass = typeClass;
        setDeclaredFields(typeClass);
    }

    /**
     * 타입 인스턴스를 반환한다.
     * @return 타입 인스턴스
     */
    public T getInstance()
    {
        T retVal = null;

        try
        {
            retVal = typeClass.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }

        return retVal;
    }

    /**
     * 타입 클래스 내 선언된 필드 리스트를 반환한다.<br>
     * 접근자와 무관하게 필드 값을 읽고 쓸 수 있다.
     * @return 타입 클래스 내 선언된 필드 리스트
     */
    public List<Field> getDeclaredFields()
    {
        return declaredFields;
    }

    /**
     * 타입 클래스 내 선언된 필드 리스트를 가져온다.
     * @param typeClass 타입 클래스
     */
    private void setDeclaredFields(final Class<T> typeClass)
    {
        declaredFields = Arrays
                .stream(typeClass.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .collect(Collectors.toList());
    }
}