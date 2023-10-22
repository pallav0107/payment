using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using authentication_api.Model; 

namespace authentication_api.Controllers;

[Route("api/register")]
[ApiController]
public class RegistrationController : ControllerBase
{
    private readonly IRegistrationService _registrationService;

    public RegistrationController(IRegistrationService registrationService)
    {
        _registrationService = registrationService;
    }

    [HttpPost]
    public IActionResult Register([FromBody] RegistrationModel model)
    {
        if (!ModelState.IsValid)
        {
            return BadRequest(ModelState);
        }

        var result = _registrationService.RegisterUser(model);

        if (result.Succeeded)
        {
            return Ok("Registration successful");
        }
        else
        {
            return BadRequest(result.Errors);
        }
    }

}
