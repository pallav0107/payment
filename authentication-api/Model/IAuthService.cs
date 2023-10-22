namespace authentication_api.Model;
public interface IAuthService
{
    UserModel Authenticate(string username, string password);
    string GenerateToken(UserModel user);
}
