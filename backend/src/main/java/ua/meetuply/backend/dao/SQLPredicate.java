package ua.meetuply.backend.dao;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SQLPredicate {

    public enum Operation {
        LESS(" < "),
        GREATER(" > "),
        EQUALS(" = "),
        IN(" IN "),
        AND(" AND "),
        OR(" OR ");

        public final String label;

        Operation(String label) { this.label = label; }
    }

    public SQLPredicate(Object lvalue, Operation operation, Object rvalue) {
        this.lvalue = lvalue;
        this.operation = operation;
        this.rvalue = rvalue;
    }

    public SQLPredicate(Object lvalue, Operation operation, List<Object> rvalues) {
        this.lvalue = lvalue;
        this.operation = operation;
        this.rvalues = rvalues;
    }

    public SQLPredicate(Operation operation, List<SQLPredicate> rvalues) {
        this.operation = operation;
        this.rvalues = new ArrayList<>(rvalues);
    }

    private Object lvalue;
    private Operation operation;
    private Object rvalue;
    private List<Object> rvalues;

    @Override
    public String toString() {
        if (operation == Operation.IN) {
            StringBuilder sb = new StringBuilder(lvalue.toString() + operation.label + "(");
            for (Object o: rvalues)
                sb.append(o.toString()).append(", ");
            sb.delete(sb.length() - 2, sb.length()).append(")");
            return sb.toString();
        }

        if (rvalues != null && (operation == Operation.AND || operation == Operation.OR)) {
            StringBuilder sb = new StringBuilder();
            for (Object o: rvalues)
                sb.append(o.toString()).append(operation.label);
            sb.delete(sb.length() - operation.label.length() + 1, sb.length());
            return sb.toString();
        }
        return lvalue.toString() + operation.label + rvalue;
    }
}
