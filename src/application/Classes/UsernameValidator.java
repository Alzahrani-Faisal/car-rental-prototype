package application.Classes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator
{
    private Pattern pattern;
    private Matcher matcher;

    private static final String USERNAME_REGEX = "^[a-z0-9_-]{3,15}$";

    public UsernameValidator()
    {
        pattern = Pattern.compile(USERNAME_REGEX);
    }

    public boolean validate(final String username)
    {
        matcher = pattern.matcher(username);
        return matcher.matches();
    }

}