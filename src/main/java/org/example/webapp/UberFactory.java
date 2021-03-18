package org.example.webapp;

public class UberFactory {
    private final static UberFactory INSTANCE = new UberFactory();

    private UberFactory() {
        this.userService = new UserService();
    }

    public static  UberFactory instance(){
        return INSTANCE;
    }

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }
}
