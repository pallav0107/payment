using System.Collections.Generic;
using authentication_api.Model; 
using System.IdentityModel.Tokens.Jwt;
using authentication_api.Entities; 

namespace authentication_api.Services;
public class AuthService : IAuthService
{
    private readonly PaymentsContext _dbContext; // Replace YourDbContext with your DbContext class
    private readonly IConfiguration _configuration;

    public AuthService(PaymentsContext dbContext, IConfiguration configuration)
    {
        _dbContext = dbContext;
        _configuration = configuration;
    }
    // Implement the methods defined in the IAuthService interface
    public UserModel Authenticate(string username, string password)
    {
        // Implement authentication logic here
        var user = _dbContext.Users.FirstOrDefault(u => u.Username == username && u.Password == password);

        if (user == null)
        {
            return null; // User not found or invalid credentials
        }

        // You can map the User entity to a UserModel or create a UserModel with the necessary properties.
        var userModel = new UserModel
        {
            UserId = user.UserId,
            Username = user.Username,
            Email = user.Email,
        };

        return userModel;
    }

    public string GenerateToken(UserModel user)
    {
        // Implement token generation logic here
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:SecretKey"]));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
        var expires = DateTime.Now.AddDays(Convert.ToDouble(_configuration["Jwt:ExpirationInDays"]));

        var token = new JwtSecurityToken(
            issuer: _configuration["Jwt:Issuer"],
            audience: _configuration["Jwt:Audience"],
            claims: new[] {
                new Claim(ClaimTypes.NameIdentifier, user.UserId.ToString()),
                new Claim(ClaimTypes.Name, user.Username),
                // Add other claims as needed
            },
            expires: expires,
            signingCredentials: creds
        );

        var tokenHandler = new JwtSecurityTokenHandler();
        return tokenHandler.WriteToken(token);
    
    }
}