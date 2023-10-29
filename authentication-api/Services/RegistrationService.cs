using authentication_api.Model;
using authentication_api.Entities;
using BCrypt.Net;

namespace authentication_api.Services
{
    public class RegistrationService : IRegistrationService
    {
        private readonly PaymentsContext _dbContext;
        private readonly IConfiguration _configuration;

        public RegistrationService(PaymentsContext dbContext, IConfiguration configuration)
         {
            _configuration = configuration;
        
            _dbContext = dbContext;
        }
        public RegisterUserResult RegisterUser(RegistrationModel model)
        {
            using var transaction = _dbContext.Database.BeginTransaction();

            try
            {
                // Check if the username is already taken
                var existingUserWithUsername = _dbContext.Users.FirstOrDefault(u => u.Email == model.Email);
                if (existingUserWithUsername != null)
                {
                    return new RegisterUserResult
                    {
                        Message = "Username is already taken.",
                        UserId = null
                    };
                }

                // Check if the email is already in use
                var existingUserWithEmail = _dbContext.Users.FirstOrDefault(u => u.Email == model.Email);
                if (existingUserWithEmail != null)
                {
                    return new RegisterUserResult
                    {
                        Message = "Email is already in use.",
                        UserId = null
                    };
                }

                // Get the role based on role name (you may want to validate this)
                var roleName = "User"; // Replace with the desired role name
                var role = _dbContext.UserRoles.FirstOrDefault(r => r.RoleName == roleName);

                if (role == null)
                {
                    return new RegisterUserResult
                    {
                        Message = "Role does not exist.",
                        UserId = null
                    };
                }

                // Create a new user
                var user = new User
                {
                    Username = model.Username,
                    Email = model.Email,
                    Password = Constants.HashPassword(model.Password),
                    PhoneNumber = model.PhoneNumber,
                    RoleId = role.RoleId
                };

                // Add the user to the context and save changes to get the user's ID
                _dbContext.Users.Add(user);
                _dbContext.SaveChanges();

                // Now, you can access the user's ID
                var userId = user.UserId;

                // Create a user profile using the obtained user ID
                var userProfile = new UserProfile
                {
                    UserId = userId, // Assign the user's ID
                    DateOfBirth = DateOnly.FromDateTime(model.DateOfBirth),
                    Gender = model.Gender,
                    Country = model.Country,
                    Bio = model.Bio,
                    ProfilePicture = model.ProfilePicture
                };

                // Add the user profile to the context and save changes
                _dbContext.UserProfiles.Add(userProfile);
                _dbContext.SaveChanges();

                // If all operations are successful, commit the transaction
                transaction.Commit();

                return new RegisterUserResult
                {
                    Message = "User created.",
                    UserId = userId // Return the user's ID
                };
            }
            catch (Exception ex)
            {
                // If any operation fails, roll back the transaction
                transaction.Rollback();

                // Log or handle the exception
                return new RegisterUserResult { Message = ex.ToString() };
            }
        }

    }
}
