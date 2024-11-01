package com.example.java_pandas.dataframe;

import java.util.*;
import java.util.stream.Collectors;

public class Index {
    private List<Object> labels;

    public Index(int size) {
        labels = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            labels.add(i);
        }

    }
    public Index(List<Object> labels) {
        this.labels = labels;
    }
    public Object get(int index) {
        return labels.get(index);
    }
    public int size(){
        return labels.size();
    }
    public boolean isUnique(){
        Set<Object> uniqueLabels = new HashSet<>(labels);
        return uniqueLabels.size() == uniqueLabels.size();
    }
    public Index unique(){
        List<Object> uniqueLabels = labels.stream().distinct().toList();
        return new Index(uniqueLabels);
    }
    public Map<String,Long> valueCounts(){
        return labels.stream().
                collect(Collectors
                        .groupingBy(label-> label.toString(),Collectors.counting()));
    }
    public Index slice(int start,int end){
        List<Object> slicedLabels= labels.subList(start,Math.min(end,labels.size()));
        return new Index(slicedLabels);
    }
    public Index intersection(Index other){
        Set<Object> unionSet = new HashSet<>(labels);
        unionSet.addAll(other.labels);
        return new Index(new ArrayList<>(unionSet));
    }


    @Override
    public String toString() {
        return labels.toString();
    }
}
