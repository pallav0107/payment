using System;
using System.Collections.Generic;
using Microsoft.Extensions.Configuration;
using authentication_api.Model;
using authentication_api.Entities;
using Microsoft.EntityFrameworkCore; // Remove the problematic line

namespace authentication_api.Services
{
    public class RegistrationService : IRegistrationService
    {
        private readonly PaymentsContext _dbContext;
        private readonly IConfiguration _configuration;

        public RegistrationService(PaymentsContext dbContext, IConfiguration configuration)
        {
            _dbContext = dbContext;
            _configuration = configuration;
        }

        public RegisterUserResult RegisterUser(RegistrationModel model)
        {
            try
            {
                // Hash the password (you should never store plain text passwords).
                string hashedPassword = HashPassword(model.Password);

                // Call the PostgreSQL stored procedure 'register_user' to create the user and get the message and user_id.
                var result = _dbContext.Set<RegisterUserResult>()
                            .FromSqlRaw("SELECT * FROM register_user({0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9})",
                                model.Username,
                                model.Email,
                                hashedPassword,
                                model.PhoneNumber,
                                null, // Role name is not provided here
                                model.DateOfBirth,
                                model.Gender,
                                model.Country,
                                model.Bio,
                                model.ProfilePicture)
                            .FirstOrDefault();

                if (result.Message == "User created.")
                {
                    // User is created, return the user_id.
                    return result;
                }
                else
                {
                    // User already exists or other error, handle accordingly.
                    throw new Exception(result.Message);
                }
            }
            catch (Exception ex)
            {
                // Handle exceptions, such as user already existing or other errors.
                return new RegisterUserResult { Message = "User registration failed." };
            }
        }

        private string HashPassword(string password)
        {
            // Hash the password using a secure hash function.
            // You should use a secure password hashing library or mechanism.
            // This is just a placeholder.
            return password;
        }
    }
}
