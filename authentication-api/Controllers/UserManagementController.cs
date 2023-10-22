using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using authentication_api.Model; 

namespace authentication_api.Controllers;

[Route("api/admin/users")]
[ApiController]
[Authorize(Roles = "Admin")] // Requires admin role
public class UserManagementController : ControllerBase
{
    private readonly IUserService _userService;

    public UserManagementController(IUserService userService)
    {
        _userService = userService;
    }

    [HttpGet]
    public IActionResult GetUsers()
    {
        return Ok();
    }

    [HttpPut("{userId}/updaterole")]
    public IActionResult UpdateUserRole(string userId, [FromBody] UpdateRoleModel model)
    {
        // Update user role
        return Ok();
    }

    [HttpDelete("{userId}")]
    public IActionResult DeleteUser(string userId)
    {
        // Delete a user
        return Ok();
    }
}
