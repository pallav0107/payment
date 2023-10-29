using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace authentication_api.Migrations
{
    /// <inheritdoc />
    public partial class UpdateSchema : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "RegisterUserResult");

            migrationBuilder.EnsureSchema(
                name: "payments");

            migrationBuilder.RenameTable(
                name: "users",
                newName: "users",
                newSchema: "payments");

            migrationBuilder.RenameTable(
                name: "user_roles",
                newName: "user_roles",
                newSchema: "payments");

            migrationBuilder.RenameTable(
                name: "user_profiles",
                newName: "user_profiles",
                newSchema: "payments");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameTable(
                name: "users",
                schema: "payments",
                newName: "users");

            migrationBuilder.RenameTable(
                name: "user_roles",
                schema: "payments",
                newName: "user_roles");

            migrationBuilder.RenameTable(
                name: "user_profiles",
                schema: "payments",
                newName: "user_profiles");

            migrationBuilder.CreateTable(
                name: "RegisterUserResult",
                columns: table => new
                {
                    Message = table.Column<string>(type: "text", nullable: true),
                    UserId = table.Column<Guid>(type: "uuid", nullable: true)
                },
                constraints: table =>
                {
                });
        }
    }
}
