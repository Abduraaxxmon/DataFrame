package com.example.java_pandas.dataframe;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Series {
    private List<Object> data;

    public Series(List<Object> data) {
        this.data = data;

    }
    public List<Object> getData(){
        return data;
    }
    public Object get(int index){
        return data.get(index);
    }
    public int size(){
        return data.size();
    }
    public double sum(){
        double sum = 0.0;
        for(Object o : data){
            if(o instanceof Double){
                sum += ((Double)o).doubleValue();
            }
        }
        return sum;
    }
    public Object max(){
        return data.stream()
                .filter(value-> value instanceof Comparable)
                .min((a,b)-> ((Comparable)a).compareTo(b))
                .orElse(null);

    }
    public Series filter(Predicate<Object> condition){
        List<Object> filteredData = data.stream()
                .filter(condition)
                .collect(Collectors.toList());
        return new Series(filteredData);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
