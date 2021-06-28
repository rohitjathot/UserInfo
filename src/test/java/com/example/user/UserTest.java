package com.example.user;

import com.example.user.controller.UserDBController;
import com.example.user.controller.Validation;
import com.example.user.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class UserTest {

    @Test
    public void addUserTest() throws SQLException, ClassNotFoundException {
        //Arrange
        User user = new User(1,25,"Rohit","Jathot");

        //Act
        UserDBController database = UserDBController.getInstance();
        database.createUser(user);

        //Assert
        User userFromDB = database.getUser(1);
        Assertions.assertNotNull(userFromDB);
        Assertions.assertEquals(user.getId(),userFromDB.getId());
        Assertions.assertEquals(user.getAge(),userFromDB.getAge());
        Assertions.assertEquals(user.getFirstName(),userFromDB.getFirstName());
        Assertions.assertEquals(user.getLastName(),userFromDB.getLastName());
    }

    @Test
    public void updateUserTest() throws SQLException, ClassNotFoundException {
        //Arrange
        User user = new User(1,20,"Rohit","Jathot");

        //Act
        UserDBController database = UserDBController.getInstance();
        database.updateUser(user);

        //Assert
        User userFromDB = database.getUser(1);
        Assertions.assertNotNull(userFromDB);
        Assertions.assertEquals(user.getAge(),userFromDB.getAge());
    }


    @Test
    public void getUserTest() throws SQLException, ClassNotFoundException {
        //Arrange
        Integer id = 1;

        //Act
        UserDBController database = UserDBController.getInstance();

        //Assert
        User userFromDB = database.getUser(1);
        Assertions.assertNotNull(userFromDB);
    }

    @Test
    public void removeUserTest() throws SQLException, ClassNotFoundException {
        //Arrange
        Integer id = 1;

        //Act
        UserDBController database = UserDBController.getInstance();
        database.removeUser(id);

        //Assert
        User userFromDB = database.getUser(1);
        Assertions.assertNull(userFromDB);
    }

    @Test
    public void validateTest() throws SQLException, ClassNotFoundException {
        //Arrange
        User user = new User(1,25,"Rohit","Jathot");
        Validation validation = Validation.getInstance();

        //Act
        boolean result = validation.validate(user);

        //Assert
        Assertions.assertTrue(result);
    }
}
