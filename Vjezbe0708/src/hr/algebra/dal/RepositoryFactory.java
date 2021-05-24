/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.SqlRepository;



/**
 *
 * @author dnlbe
 */
public class RepositoryFactory {

    private RepositoryFactory() {
    }
    
    public static Repository getRepository() throws Exception {
        return new SqlRepository();
     }
}
