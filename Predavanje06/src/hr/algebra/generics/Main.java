/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.generics;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author doss
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<String> names = new ArrayList<>(); // Diamond operator
        // postoji samo u compile timeu!!!
        // Java generics postoje samo u compile time radi backwards compatibility
        // ispod haube je to lista objekata, sto se tice runtimea, compiletime jedino zna za generics
        
        holderDemo();
        holderHack();
        
        //names.add(1);
        names.add("Pero");
        //names.add(new Main());
        
        for (Object name : names) {
            printGeneric(name);
        }
    }

    private static<T> void printGeneric(T t) {
        System.out.println(t);
    }
    
    private static void holderDemo() {
        Holder<Integer> intHolder = new Holder<>(1);
        //holder.setValue("Miroslav");
        printGeneric(intHolder);

        Holder<Double> doubleHolder = new Holder<>(Double.MAX_VALUE);
        doubleHolder.setValue(Double.MIN_VALUE);
        printGeneric(doubleHolder);
        
        Holder<Long> longHolder = new Holder<>(Long.MAX_VALUE);
        longHolder.setValue(Long.MIN_VALUE);
        printGeneric(longHolder);
        
//        Holder<String> stringHolder = new Holder<>("Miroslav");
//        stringHolder.setValue("Skare Ozbolt");
//        printGeneric(longHolder);
    }

    private static void holderHack() {
        Integer i = 1;
        assign(i);
        System.out.println(i);
        
        Integer[] ints = new Integer[1];
        assignArray(ints);
        System.out.println(ints[0]);
        
        Holder<Integer> holder = new Holder<>(5);
        assignHolder(holder);
        System.out.println(holder);
    }

    private static void assign(int i) {
        i = 5;
    }

    private static void assignArray(Integer[] ints) {
        ints[0] = 55;
    }

    private static void assignHolder(Holder<Integer> holder) {
        holder.setValue(5555);
    }
    
}
