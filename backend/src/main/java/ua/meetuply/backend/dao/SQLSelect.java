package ua.meetuply.backend.dao;

import java.util.Collections;
import java.util.List;

public class SQLSelect {

    private String table;
    private List<Object> attributes;
    private SQLPredicate where;
    private List<String> groupBy;
    private SQLPredicate having;

    public SQLSelect(String table, Object attr, SQLPredicate where) {
        this.table = table;
        this.attributes = Collections.singletonList(attr);
        this.where = where;
    }

    public SQLSelect(String table, List<Object> attributes, SQLPredicate where, List<String> groupBy, SQLPredicate having) {
        this.table = table;
        this.attributes = attributes;
        this.where = where;
        this.groupBy = groupBy;
        this.having = having;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SELECT ");
        if (attributes.isEmpty()) sb.append("* ");
        else for (Object o: attributes) sb.append(o.toString()).append(", ");
        sb.delete(sb.length() - 2, sb.length());
        sb.append(" FROM ").append(table).append(" ");
        if (where != null) sb.append(" WHERE ").append(where);
        if (groupBy != null && !groupBy.isEmpty()){
            sb.append(" GROUP BY ");
            for (String attr: groupBy) sb.append(attr).append(", ");
            sb.delete(sb.length() - 2, sb.length()).append(" ");
        }
        if (having != null) sb.append(having).append(" ");
        return sb.toString();
    }
}
