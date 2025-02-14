
import Model.Character;
import Util.ConnectionUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import Util.FileUtil;

import static org.junit.Assert.fail;

public class Lab2Test {
    private Connection conn;

    @Test
    public void problem2() {
        try {
            List<Character> expectedList = new LinkedList<>();
            expectedList.add(new Character(5, "Feyd-Rautha", "Harkonnen"));
            expectedList.add(new Character(3, "Jessica", "Atreides"));
            expectedList.add(new Character(1, "Leto", "Atreides"));



            String sql = FileUtil.parseSQLFile("src/main/lab2.sql");
            if(sql.isBlank()){
                Assert.fail("No SQL statement exists in lab2.sql.");
            }
            List<Character> resultList = new LinkedList<>();
            try {
                Connection connection = ConnectionUtil.getConnection();
                Statement s = connection.createStatement();
                ResultSet rs =s.executeQuery(sql);
                while(rs.next()) {
                    resultList.add(new Character(rs.getInt(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException e) {
                Assert.fail("There was an issue with your SQL statement in lab2.sql:"+e.getMessage());
            }
            Assert.assertEquals(expectedList, resultList);
        }catch(Exception e) {
            Assert.fail("There was an issue running the test for lab2.sql: "+e.getStackTrace());
        }
    }




    @Before
    public void beforeEach() {
        try {
            conn = ConnectionUtil.getConnection();

            String createTable = "CREATE TABLE character (" +
                    "id SERIAL PRIMARY KEY," +
                    "first_name VARCHAR(255)," +
                    "last_name VARCHAR(255)" +
                    ");";
            PreparedStatement createTableStatement = conn.prepareStatement(createTable);
            createTableStatement.executeUpdate();

            String insertData = "INSERT INTO character (first_name, last_name) VALUES" +
                    "('Leto', 'Atreides')," +
                    "('Vladimir', 'Harkonnen')," +
                    "('Jessica', 'Atreides')," +
                    "('Paul', 'Atreides')," +
                    "('Feyd-Rautha', 'Harkonnen');";
            PreparedStatement insertDataStatement = conn.prepareStatement(insertData);
            insertDataStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void afterEach() {
        try {
            conn = ConnectionUtil.getConnection();

            String dropTable = "DROP TABLE IF EXISTS character";
            PreparedStatement createTableStatement = conn.prepareStatement(dropTable);
            createTableStatement.executeUpdate();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}