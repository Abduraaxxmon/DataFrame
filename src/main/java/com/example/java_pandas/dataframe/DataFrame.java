package com.example.java_pandas.dataframe;

import java.util.*;
import java.util.function.Predicate;

public class DataFrame {
    private List<String> columns;
    private Map<String,Series> data;
    private Index index;

    public DataFrame(List<String> columns, List<List<Object>> rows){
        this.columns = columns;
        this.data = new HashMap<>();
        for (int i =0 ; i<columns.size();i++){
            List<Object> columnData= new ArrayList<>();
            for(List<Object> row: rows){
                columnData.add(row.get(i));
            }
            this.data.put(columns.get(i), new Series(columnData));

        }
        this.index = new Index(rows.size());

    }
    public Series getColumn(String columnName){
        return data.get(columnName);
    }
    public Object getValue(int row,String columnName){
        return data
                .get(columnName)
                .get(row);
    }
    public void addColumn(String columnName,List<Object> values){
        if(values.size() != columns.size()){
            throw new RuntimeException("Number of values does not match number of columns");
        }
        columns.add(columnName);
        data.put(columnName, new Series(values));
    }

    public void removeColumn(String columnName){
        columns.remove(columnName);
        data.remove(columnName);
    }
    public DataFrame sliceRows(int startRow, int endRow){
        List<List<Object>> slicedRows = new ArrayList<>();
        for (int i=startRow ; i<endRow ; i++){
            List<Object> row= new ArrayList<>();
            for (String column : columns){
                row.add(data.get(column).get(i));
            }
            slicedRows.add(row);
        }
        return new DataFrame(columns,slicedRows);
    }
    public Double mean(String columnName){
        Series series = getColumn(columnName);
        return series.getData().stream()
                .filter(obj-> obj instanceof Number)
                .mapToDouble(obj-> ((Number) obj).doubleValue())
                .average()
                .orElse(Double.NaN);

    }
    public FilterBuilder filter(String columnName ){
        return new FilterBuilder(columnName);
    }
    public class FilterBuilder {
        private String columnName;
        private Predicate<Object> predicate;

        public FilterBuilder(String columnName) {
            this.columnName = columnName;
        }
        public DataFrame bigger(Number value){
            this.predicate = obj -> obj instanceof Number &&((Number)obj)
                    .doubleValue()> value.doubleValue();
            return applyFilter();
        }

        private DataFrame applyFilter() {
            List<List<Object>> filteredRows = new ArrayList<>();
            for (int i = 0 ; i < index.size();i++){
                if (predicate.test(data.get(columnName).get(i))){
                    List<Object> row = new ArrayList<>();
                    for (String column : columns){
                        row.add(data.get(column).get(i));
                    }
                    filteredRows.add(row);
                }
            }
            return new DataFrame(columns,filteredRows);

        }

        @Override
        public String toString() {
            return "FilterBuilder{" +
                    "columnName='" + columnName + '\'' +
                    ", predicate=" + predicate +
                    '}';
        }

    }

    @Override
    public String toString() {
        // Calculate maximum width for each column
        Map<String, Integer> columnWidths = new HashMap<>();
        for (String column : columns) {
            int maxWidth = column.length(); // Start with column name length
            for (Object value : data.get(column).getData()) {
                maxWidth = Math.max(maxWidth, value.toString().length());
            }
            columnWidths.put(column, maxWidth);
        }

        // Build the table header
        StringBuilder sb = new StringBuilder();
        for (String column : columns) {
            sb.append(String.format("%-" + columnWidths.get(column) + "s | ", column));
        }
        sb.append("\n");

        // Create separator line
        for (String column : columns) {
            sb.append("-".repeat(columnWidths.get(column)) + "-+-");
        }
        sb.setLength(sb.length() - 2);  // Remove the trailing '+' and '-'
        sb.append("\n");

        // Build each row of data
        for (int i = 0; i < index.size(); i++) {
            for (String column : columns) {
                Object value = data.get(column).get(i);
                sb.append(String.format("%-" + columnWidths.get(column) + "s | ", value));
            }
            sb.setLength(sb.length() - 2);  // Remove the trailing '|'
            sb.append("\n");
        }

        return sb.toString();
    }
}
