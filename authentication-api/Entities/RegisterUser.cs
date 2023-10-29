using System;
using System.Collections.Generic;

namespace authentication_api.Entities
{
    public partial class RegisterUser
    {
        public Guid UserId { get; set; }

        public string Username { get; set; } = null!;

        public string Email { get; set; } = null!;

        public string Password { get; set; } = null!;

        public string? PhoneNumber { get; set; }

        public Guid? RoleId { get; set; }

        public virtual UserRole? Role { get; set; }

        public virtual UserProfile? UserProfile { get; set; }

        // Additional properties that correspond to the parameters of the register_user stored procedure
        public string? RoleName { get; set; }
        public DateTime DateOfBirth { get; set; }
        public char Gender { get; set; }
        public string Country { get; set; }
        public string Bio { get; set; }
        public byte[] ProfilePicture { get; set; }
    }
}
