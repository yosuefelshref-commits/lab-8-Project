package models;
import java.util.ArrayList;

public abstract class User
{
    private int userId;
    private String userName;
    private String email;
    private String password;
    private boolean isLoggedIn;
    public static ArrayList<User> users = new ArrayList<>();

    public User(int userId, String userName, String email, String password)
    {
        this.userId = userId;
        this.userName = userName;
        this.email = email ;
        this.password = password;
        this.isLoggedIn = false;
        users.add(this);
    }

    public void setUserId(int userId){this.userId = userId;}
    public int getUserId(){return userId;}
    public void setUserName(String userName){this.userName = userName;}
    public String getUserName(){return userName;}
    public void setEmail(String email){this.email = email;}
    public String getEmail(){return email;}
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return password;}

    public abstract String getRole();

    public static User login(String email, String password)
    {
        for(User u : users)
        {
            if(u.email.equals(email) && u.password.equals(password))
            {
                u.isLoggedIn = true;
                System.out.println("Logged In Successfully...");
                return u;
            }
        }
        System.out.println("Wrong Email Or Password");
        return null;
    }


    public void logout()
    {
        if(isLoggedIn)
        {
            isLoggedIn = false;
            System.out.println(userName + " Logged Out Successfully..");
        }
        else
            System.out.println("User Is Not Logged In");
    }
}
