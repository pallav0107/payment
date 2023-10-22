namespace authentication_api.Model
{
    public interface IRegistrationService
    {
        RegistrationResult RegisterUser(RegistrationModel model);
    }

    public class RegistrationResult
    {
        public bool Succeeded { get; set; }
        public List<string>? Errors { get; set; }
    }
}
