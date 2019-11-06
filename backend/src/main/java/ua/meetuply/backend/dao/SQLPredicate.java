package ua.meetuply.backend.dao;

import lombok.Getter;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class SQLPredicate {

    public enum Operation {
        LESS(" < "),
        GREATER(" > "),
        EQUALS(" = "),
        IN(" IN "),
        AND(" AND "),
        OR(" OR "),
        EXISTS(" EXISTS"),
        LESS_EQUALS(" <= "),
        GREATER_EQUALS(" >= "),
        NOT_EQUALS(" <> ");

        public final String label;

        Operation(String label) { this.label = label; }
    }

    public SQLPredicate(Object lvalue, Operation operation, Object rvalue) {
        this.lvalue = lvalue;
        this.operation = operation;
        this.rvalue = rvalue;
    }

    public SQLPredicate(Operation operation, Object rvalue) {
        this.operation = operation;
        this.rvalue = rvalue;
    }

    private Object lvalue;
    private Operation operation;
    private Object rvalue;

    private static DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public String toString() {
        if (operation == Operation.IN) {
            if (rvalue instanceof SQLSelect) {
                StringBuilder sb = new StringBuilder(lvalue.toString() + operation.label);
                sb.append("(").append(rvalue.toString()).append(")");
                return sb.toString();
            }

            if (rvalue != null && rvalue instanceof List) {
                StringBuilder sb = new StringBuilder(lvalue.toString() + operation.label + "(");
                for (Object o : (List) rvalue)
                    sb.append(o.toString()).append(", ");
                sb.delete(sb.length() - 2, sb.length()).append(")");
                return sb.toString();
            }
        }

        if (rvalue != null && rvalue instanceof List && (operation == Operation.AND || operation == Operation.OR)) {
            StringBuilder sb = new StringBuilder();
            for (Object o: (List) rvalue)
                sb.append(o.toString()).append(operation.label);
            sb.delete(sb.length() - operation.label.length() + 1, sb.length());
            return sb.toString();
        }

        if (rvalue != null && rvalue instanceof SQLSelect && operation == Operation.EXISTS) {
            StringBuilder sb = new StringBuilder(operation.label + " (" + rvalue.toString() + ")");
            return sb.toString();
        }

        if (rvalue instanceof Timestamp) {
            Timestamp t = (Timestamp) rvalue;
            return lvalue.toString() + operation.label + "'" + formatter.format(t.toLocalDateTime()) + "'";
        }
        return lvalue.toString() + operation.label + rvalue;
    }
}
