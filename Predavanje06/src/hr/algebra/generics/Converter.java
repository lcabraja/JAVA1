/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.generics;

/**
 *
 * @author doss
 */
public interface Converter<T, R> {
    T convert(R input);
    
}
