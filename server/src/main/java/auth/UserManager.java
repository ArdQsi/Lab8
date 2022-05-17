package auth;

import exceptions.DatabaseException;

import java.util.List;

public interface UserManager {
    public void add(User user) throws DatabaseException;
    public boolean isValid(User user);
    public boolean isPresent(String userName);
    public List<User> getUsers();
}
