using System;
using System.Collections.Generic;

namespace authentication_api.Entities;

public partial class User
{
    public Guid UserId { get; set; }

    public string Username { get; set; } = null!;

    public string Email { get; set; } = null!;

    public string Password { get; set; } = null!;

    public string? PhoneNumber { get; set; }

    public Guid? RoleId { get; set; }

    public virtual UserRole? Role { get; set; }

    public virtual UserProfile? UserProfile { get; set; }
}
