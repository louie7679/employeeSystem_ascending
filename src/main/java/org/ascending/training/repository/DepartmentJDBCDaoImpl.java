package org.ascending.training.repository;

import org.ascending.training.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentJDBCDaoImpl implements IDepartmentDao{
    static final String DB_URL = "jdbc:postgresql://localhost:5431/training_db";
    static final String USER = "admin";
    static final String PASS = "Training123!";

    @Override
    public void save(Department department) {

    }

    @Override
    public List<Department> getDepartments() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.debug("Start to getDepartments from Postgres via JDBC.");
        //Step1: Prepare the required data model
        List<Department> departments = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            //Step2: Open a connection ->5 key points to connect db
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //Step3: Execute a query
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM departments";
            rs = stmt.executeQuery(sql);
            logger.info("Connects to DB successfully and execute the query.");

            //Step4: Extract data from result set
            while(rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                String location = rs.getString("location");

                Department department = new Department();
                department.setId(id);
                department.setName(name);
                department.setDescription(description);
                department.setLocation(location);
                departments.add(department);
            }

        } catch (SQLException e) {
            logger.error("Unable to connect to db or execute query", e);
            //e.printStackTrace();
        } finally {
            //Step6: finally block used to close resources
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(conn != null) conn.close();
            } catch (SQLException e) {
                logger.error("Unable to close db connection", e);
                //e.printStackTrace();
            }
        }
        logger.info("Finish getDepartments {}", departments);
        return departments;
    }

    @Override
    public Department getById(Long id) {
        return null;
    }

    @Override
    public void delete(Department department) {

    }

    @Override
    public Department getDepartmentEagerBy(Long id) {
        return null;
    }

    @Override
    public Department update(Department department) {
        return null;
    }
}