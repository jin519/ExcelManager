import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * ----------------------------------<br>
 * | field | field | field | field |...<br>
 * ----------------------------------<br>
 * |content|content|content|content|... <- record[id]<br>
 * ----------------------------------<br>
 * |content|content|content|content|... <- record[id]<br>
 * ----------------------------------
 */
public class Table <T>
{
    private List<String> fields = new ArrayList<>();
    private TreeMap<String, T> idRecordMap = new TreeMap<>();

    public Table() {}

    public Table(final List<String> fields)
    {
        setFields(fields);
    }

    public List<String> getFields()
    {
        return fields;
    }

    public void setFields(final List<String> fields)
    {
        this.fields.clear();
        this.fields.addAll(fields);
    }

    public void addField(final String field)
    {
        fields.add(field);
    }

    public Set<String> getIds()
    {
        return idRecordMap.keySet();
    }

    public T getRecord(final String id)
    {
        return idRecordMap.get(id);
    }

    public void set(final String id, final T record)
    {
        idRecordMap.put(id, record);
    }

    @Override
    public String toString()
    {
        return "Table{" +
                "fields=" + fields +
                ", idRecordMap=" + idRecordMap +
                '}';
    }
}
