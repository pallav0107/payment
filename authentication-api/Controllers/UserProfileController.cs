using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using authentication_api.Model;
using System.Threading.Tasks;

namespace authentication_api.Controllers
{
    [Route("api/profile")]
    [ApiController]
    [Authorize] // Requires authentication
    public class UserProfileController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserProfileController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpGet]
        public async Task<IActionResult> GetUserProfile()
        {
            // Check if User.Identity and User.Identity.Name are not null before using them.
            if (User.Identity != null && User.Identity.Name != null)
            {
                var user = await _userService.GetUserById(User.Identity.Name);
                if (user != null)
                {
                    // Return user profile information
                    return Ok(user);
                }
            }

            return NotFound("User not found");
        }

        [HttpPost("update")]
        public async Task<IActionResult> UpdateUserProfile([FromBody] UserProfileModel model)
        {
            if (ModelState.IsValid)
            {
                // Check if User.Identity and User.Identity.Name are not null before using them.
                if (User.Identity != null && User.Identity.Name != null)
                {
                    await _userService.UpdateUserProfile(User.Identity.Name, model);
                    return Ok("User profile updated successfully");
                }

                // Handle the case where User.Identity or User.Identity.Name is null
                return BadRequest("User identity not found.");
            }
            return BadRequest(ModelState);
        }

        [HttpPost("changepassword")]
        public async Task<IActionResult> ChangePassword([FromBody] ChangePasswordModel changePasswordModel)
        {
            if (ModelState.IsValid)
            {
                // Check if User.Identity and User.Identity.Name are not null before using them.
                if (User.Identity != null && User.Identity.Name != null)
                {
                    // Call the service to change the user's password.
                    await _userService.ChangePassword(User.Identity.Name, changePasswordModel.NewPassword);
                    return Ok("Password changed successfully");
                }

                // Handle the case where User.Identity or User.Identity.Name is null
                return BadRequest("User identity not found.");
            }
            return BadRequest(ModelState);
        }
    }
}
