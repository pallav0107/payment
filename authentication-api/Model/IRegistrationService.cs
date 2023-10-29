using authentication_api.Entities;

namespace authentication_api.Model
{
    public interface IRegistrationService
    {
        RegisterUserResult RegisterUser(RegistrationModel model);
    }
}
