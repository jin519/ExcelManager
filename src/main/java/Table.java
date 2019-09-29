import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * ---------------------------------<br>
 * | header| header| header| header|...<br>
 * ---------------------------------<br>
 * |content|content|content|content|... <- record[id]<br>
 * ---------------------------------<br>
 * |content|content|content|content|... <- record[id]<br>
 * ----------------------------------
 * @author 원진
 * @contact godjin519@gmail.com
 * @param <T> 레코드 자료구조
 */
public class Table <T>
{
    private List<String> headers = new ArrayList<>();
    private TreeMap<String, T> idRecordMap = new TreeMap<>();
    private Class<T> recordClass;

    /**
     * 생성자
     * @param recordClass 레코드 자료구조 클래스 정보
     */
    public Table(final Class<T> recordClass)
    {
        this.recordClass = recordClass;
    }

    /**
     * id, 레코드를 설정한다.
     * @param id id
     * @param record 레코드
     */
    public void set(final String id, final T record)
    {
        idRecordMap.put(id, record);
    }

    /**
     * 헤더 목록을 반환한다.
     * @return 헤더 목록
     */
    public List<String> getHeaders()
    {
        return headers;
    }

    /**
     * 헤더 목록을 설정한다.
     * @param headers 헤더 목록
     */
    public void setHeaders(final List<String> headers)
    {
        this.headers.clear();
        this.headers.addAll(headers);
    }

    /**
     * 헤더를 추가한다.
     * @param header 헤더
     */
    public void addHeader(final String header)
    {
        headers.add(header);
    }

    /**
     * id 목록을 반환한다.
     * @return id 목록
     */
    public Set<String> getIds()
    {
        return idRecordMap.keySet();
    }

    /**
     * 레코드를 반환한다.
     * @param id id
     * @return 레코드
     */
    public T getRecord(final String id)
    {
        return idRecordMap.get(id);
    }

    /**
     * 레코드 자료구조 클래스 정보를 반환한다.
     * @return 레코드 자료구조 클래스 정보
     */
    public Class<T> getRecordClass()
    {
        return recordClass;
    }
}
