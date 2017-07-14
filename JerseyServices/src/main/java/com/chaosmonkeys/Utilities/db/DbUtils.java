package com.chaosmonkeys.Utilities.db;


import com.chaosmonkeys.dao.Experiment;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.DB;

import java.util.List;
import java.util.ResourceBundle;

public class DbUtils {

    // JDBC connection properties
    private static String url;
    private static String username;
    private static String password;
    private static String driver;
    private static String dbName;
    private static ResourceBundle rb = ResourceBundle.getBundle("com.chaosmonkeys.Utilities.db.db-config");

    // initialize the driver
    static{
        dbName = rb.getString("jdbc.dbName");
        url = rb.getString("jdbc.baseurl") + rb.getString("jdbc.dbName");
        username = rb.getString("jdbc.username");
        password = rb.getString("jdbc.password");
        driver = rb.getString("jdbc.driver");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * open ActiveJDBC connection when needed in one thread
     * this method only aimed to use the Configuration database
     * if you wanna use another database, refactor it or write a
     * new method
     */
    public static void openConnection(){
        new DB(dbName).open(driver, url, username, password);
    }

    /**
     * Close ActiveJDBC connection in current thread
     */
    public static void closeConnection(){
        Base.close();
    }

    /**
     * This method would provide the experiment dao model
     * notice you should only use it when you can ensure the model existed in database
     * @param experimentName
     * @return Experiment DAO model with the specific experiment name
     */
    public static Experiment getExperimentModelByName(String experimentName){
        List<Experiment> expList = Experiment.where("experiment_name = ?", experimentName);
        Experiment exp = expList.get(0);
        return exp;
    }

}
