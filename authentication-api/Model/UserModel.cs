namespace authentication_api.Model;

public class UserModel
{
     public string? UserId { get; set; }  // Nullable
     public string? Username { get; set; }  // Nullable
     public string? Email { get; set; }  // Nullable
     public string? Password { get; set; }  // Nullable
     public string? Role { get; set; }  // Nullable
        // Add other user properties as needed
}
