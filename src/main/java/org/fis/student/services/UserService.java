package org.fis.student.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis.student.exceptions.*;
import org.fis.student.model.Trip;
import org.fis.student.model.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("travel-agency-users.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static ObjectRepository<User> getUserRepository() {
        return userRepository;
    }

    public static void addUser(String username, String password, String role, String name, String address, String email, String phone, String password2) throws UsernameAlreadyExistsException, EmailAlreadyUsedException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkEmptyTextfields(username,password,role,name,address,email,phone);
        checkUserDoesNotAlreadyExist(username);
        checkUsedEmail(email);
        checkPasswordConfirmation(password,password2);
        userRepository.insert(new User(username, encodePassword(username, password), role, name, address, phone, email));
    }

    public static String getLoggedUser(String username){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                return username;
        }
        return "";
    }

    public static String getUserRole(String username){
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                if(Objects.equals(user.getRole(),"Customer"))
                    return "Customer";
                else
                    return "Travel Agency";
        }
        return "";
    }

    private static void checkEmptyTextfields(String username, String password, String role, String name, String address, String email, String phone) throws EmptyTextfieldsException{
        if( Objects.equals(username,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(password,""))
            throw new EmptyTextfieldsException();
        else if( !( Objects.equals(role,"Travel Agency") || Objects.equals(role,"Customer") ))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(name,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(address,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(email,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(phone,""))
            throw new EmptyTextfieldsException();
    }
    private static void checkPasswordConfirmation(String password, String password2) throws WrongPasswordConfirmationException {
        if( !Objects.equals(password,password2))
            throw new WrongPasswordConfirmationException();
    }
    private static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    private static void checkUsedEmail(String email) throws EmailAlreadyUsedException{
        for (User user : userRepository.find()) {
            if (Objects.equals(email, user.getEmail()))
                throw new EmailAlreadyUsedException();
        }
    }

    public static void checkUserCredentials(String username,String password,String role) throws UsernameDoesNotExistException, WrongPasswordException, WrongRoleException {
        int oku=0,okp=0,okr=0;
        for(User user : userRepository.find()){
            if(Objects.equals(username,user.getUsername())) {
                oku = 1;
                if(Objects.equals(role,user.getRole()))
                    okr = 1;
            }
            if(Objects.equals(encodePassword(username,password),user.getPassword()))
                okp = 1;
        }
        if( oku == 0 )
            throw new UsernameDoesNotExistException(username);
        if( okr == 0 )
            throw new WrongRoleException();
        if ( okp == 0 )
            throw new WrongPasswordException();

    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }


}
