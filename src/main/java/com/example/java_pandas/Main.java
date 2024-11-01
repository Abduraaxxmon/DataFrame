package com.example.java_pandas;

import com.example.java_pandas.dataframe.DataFrame;

import java.util.Arrays;
import java.util.List;

public class Main {

        public static void main(String[] args) {
            List<String> list = Arrays.asList("Name","Age","Salary");
            List<List<Object>> lists = Arrays.asList(
                    Arrays.asList("Alice",30,70000),
                    Arrays.asList("Bob",12,121212),
                    Arrays.asList("Abdurahmon",45,65435)
            );
            DataFrame df = new DataFrame(list,lists);

//            System.out.println("age Mean"+ df.mean("Age"));
            System.out.println("age Mean" + df.filter("Age").bigger(25));
        }
    }
