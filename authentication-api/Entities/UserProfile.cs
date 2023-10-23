using System;
using System.Collections.Generic;

namespace authentication_api.Entities;

public partial class UserProfile
{
    public Guid UserId { get; set; }

    public DateOnly? DateOfBirth { get; set; }

    public char? Gender { get; set; }

    public string? Country { get; set; }

    public string? Bio { get; set; }

    public byte[]? ProfilePicture { get; set; }

    public virtual User User { get; set; } = null!;
}
