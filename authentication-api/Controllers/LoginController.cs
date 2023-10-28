using Microsoft.AspNetCore.Mvc;
using authentication_api.Model;
 
namespace authentication_api.Controllers
{
    [Route("api/login")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly IAuthService _authService;
        private readonly ILogger<LoginController> _logger; // Add a logger

        public LoginController(IAuthService authService, ILogger<LoginController> logger)
        {
            _authService = authService;
            _logger = logger;
        }

        [HttpPost]
        public IActionResult Login([FromBody] LoginModel model)
        {
            if (model != null && model.Username != null && model.Password != null)
            {
                var user = _authService.Authenticate(model.Username, model.Password);

                if (user == null)
                {
                    _logger.LogWarning("Invalid login attempt for user: {Username}", model.Username);
                    return Unauthorized("Invalid credentials");
                }

                var token = _authService.GenerateToken(user);

                _logger.LogInformation("User {Username} has successfully logged in.", model.Username);
                return Ok(new { token });
            }
            else
            {
                _logger.LogWarning("Username and password cannot be null.");
                return BadRequest("Username and password cannot be null.");
            }
        }
    }
}