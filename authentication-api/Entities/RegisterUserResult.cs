using System;
using System.Collections.Generic;

namespace authentication_api.Entities
{
    public class RegisterUserResult
    {
        public Guid? UserId { get; set; }
        public string? Message { get; set; }
    }
}
