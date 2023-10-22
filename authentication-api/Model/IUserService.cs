using System.Collections.Generic;

namespace authentication_api.Model
{
    public interface IUserService
    {
        Task<List<UserProfileModel>> GetUserById(string userId);
        List<UserModel> GetUsers();
        bool UpdateUserRole(string userId, UpdateRoleModel model);
        bool DeleteUser(string userId);

        Task UpdateUserProfile(string userName, UserProfileModel model);
        Task ChangePassword(string userName, string newPassword);
    }
}
