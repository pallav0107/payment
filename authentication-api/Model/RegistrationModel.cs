namespace authentication_api.Model
{
    public class RegistrationModel
    {
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string PhoneNumber { get; set; }
        public DateTime DateOfBirth { get; set; }
        public char Gender { get; set; }
        public string Country { get; set; }
        public string Bio { get; set; }
        public byte[]? ProfilePicture { get; set; }
        public RegistrationModel()
         {
            Username = "";    // You can provide a default value.
            Password = "";    // You can provide a default value.
                // Email is left as nullable or initialize it if you want.
         }
    }

}
