package operations;

import java.sql.*;

/**
 * Created by Administrator on 2016/4/29 0029.
 */
public class JDBCOperation {
    static class Student {
        private String Id;
        private String Name;
        private String Sex;
        private String Age;

        Student(String Name, String Sex, String Age) {

            this.Id = null;
            this.Name = Name;
            this.Sex = Sex;
            this.Age = Age;
        }

        public String getId() {
            return Id;
        }

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String sex) {
            this.Sex = sex;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            this.Name = name;
        }

        public String getAge() {
            return Age;
        }

        public void setAge(String age) {
            this.Age = age;
        }
    }

    private static Connection getConnection(){
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost/samp_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=true";
        String user = "root";
        String password = "rayest1990";
        Connection connection = null;

        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    private static int insert(Student student){
        Connection connection = getConnection();
        int i = 0;
        String sql = "insert students (Name, Sex, Age) values (?, ?, ?)";
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSex());
            preparedStatement.setString(3, student.getAge());
            i = preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static int update(Student student){
        Connection connection = getConnection();
        int i = 0;
        String sql = "update students set Age = '" + student.getAge() + " ' where Name = '" + student.getName() + "'";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sql);
            i = preparedStatement.executeUpdate();
            System.out.println("result: " + i);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    private static Integer getAll(){
        Connection connection = getConnection();
        String sql = "select * from students";
        PreparedStatement preparedStatement;
        try{
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            int column = resultSet.getMetaData().getColumnCount();
            System.out.println("====================");
            while(resultSet.next()){
                for (int i = 1; i <= column; i++){
                    System.out.print(resultSet.getString(i) + "\t");
                    if ((i == 2) && resultSet.getString(i).length() < 8){
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("====================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static int delete(String name){
        Connection connection = getConnection();
        int i = 0;
        String sql = "delete from students where Name = ' " + name + " ' ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            i = preparedStatement.executeUpdate();
            System.out.println("result: " + i);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;

    }



    public static void main(String[] args) {
        JDBCOperation.getAll();
        JDBCOperation.insert(new Student("Achilles","Male","14"));

        JDBCOperation.getAll();
        JDBCOperation.update(new Student("Bean", "", "7"));

        JDBCOperation.delete("Achilles");
        JDBCOperation.getAll();
    }
}

