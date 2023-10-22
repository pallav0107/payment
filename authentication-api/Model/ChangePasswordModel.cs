namespace authentication_api.Model
{
    public class ChangePasswordModel
    {
        public string OldPassword { get; set; } // Make this property nullable if you want to allow null values

        public string NewPassword { get; set; } // Make this property nullable if you want to allow null values
 
        public ChangePasswordModel()
        {
            OldPassword = "";    // You can provide a default value.
            NewPassword = "";    // You can provide a default value.
            // Email is left as nullable or initialize it if you want.
        }
   }
}
