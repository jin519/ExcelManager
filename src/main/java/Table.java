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
 */
public class Table <T>
{
    private List<String> headers = new ArrayList<>();
    private TreeMap<String, T> idRecordMap = new TreeMap<>();
    private Class<T> recordClass;

    public Table(final Class<T> recordClass)
    {
        this.recordClass = recordClass;
    }

    public void set(final String id, final T record)
    {
        idRecordMap.put(id, record);
    }

    public List<String> getHeaders()
    {
        return headers;
    }

    public void setHeaders(final List<String> headers)
    {
        this.headers.clear();
        this.headers.addAll(headers);
    }

    public void addHeader(final String header)
    {
        headers.add(header);
    }

    public Set<String> getIds()
    {
        return idRecordMap.keySet();
    }

    public T getRecord(final String id)
    {
        return idRecordMap.get(id);
    }

    public Class<T> getRecordClass()
    {
        return recordClass;
    }

    @Override
    public String toString()
    {
        return "Table{" +
                "headers=" + headers +
                ", idRecordMap=" + idRecordMap +
                ", recordClass=" + recordClass +
                '}';
    }
}
