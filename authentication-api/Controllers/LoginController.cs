using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using authentication_api.Model; 

namespace authentication_api.Controllers
{
    [Route("api/login")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly IAuthService _authService;

        public LoginController(IAuthService authService)
        {
            _authService = authService;
        }

        [HttpPost]
        public IActionResult Login([FromBody] LoginModel model)
        {
            if (model != null && model.Username != null && model.Password != null)
            {
                var user = _authService.Authenticate(model.Username, model.Password);

                if (user == null)
                {
                    return Unauthorized("Invalid credentials");
                }

                var token = _authService.GenerateToken(user);

                return Ok(new { token });
            }
            else
            {
                return BadRequest("Username and password cannot be null.");
            }
        }
    }
}
