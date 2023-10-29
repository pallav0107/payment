public static class Constants
    {
    public static bool VerifyPassword(string enteredPassword, string hashedPassword)
    {
        return BCrypt.Net.BCrypt.Verify(enteredPassword, hashedPassword);
    }
    public static string HashPassword(string password)
    {
            // Generate a salt for hashing
        string salt = BCrypt.Net.BCrypt.GenerateSalt();

            // Hash the password using the generated salt
        string hashedPassword = BCrypt.Net.BCrypt.HashPassword(password, salt);

        return hashedPassword;
    }
}