using System;
using System.Collections.Generic;

namespace authentication_api.Entities;

public partial class UserRole
{
    public Guid RoleId { get; set; }

    public string RoleName { get; set; } = null!;

    public virtual ICollection<User> Users { get; set; } = new List<User>();
}
