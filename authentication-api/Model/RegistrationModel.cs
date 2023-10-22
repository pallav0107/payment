namespace authentication_api.Model
{
    public class RegistrationModel
    {
        public string Username { get; set; }
        public string Password { get; set; }
        public string? Email { get; set; } // Make this property nullable if you want to allow null values
        public RegistrationModel()
        {
            Username = "";    // You can provide a default value.
            Password = "";    // You can provide a default value.
            // Email is left as nullable or initialize it if you want.
        }
    }
}
