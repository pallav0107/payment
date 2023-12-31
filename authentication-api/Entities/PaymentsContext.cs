﻿using System;
using System.Collections.Generic;
using System.Data;
using Microsoft.EntityFrameworkCore;
using System.Data.SqlClient;
using System.Data.Common;
using Npgsql;
using NpgsqlTypes;

namespace authentication_api.Entities
{
    public partial class PaymentsContext : DbContext
    {
        private readonly IConfiguration _configuration;

        public PaymentsContext(DbContextOptions<PaymentsContext> options, IConfiguration configuration)
            : base(options)
        {
            _configuration = configuration;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                // Use the configuration object to retrieve the connection string
                var connectionString = _configuration.GetConnectionString("DefaultConnection");
                optionsBuilder.UseNpgsql(connectionString);
            }
        }

        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<UserProfile> UserProfiles { get; set; }
        public virtual DbSet<UserRole> UserRoles { get; set; }

//       public RegisterUserResult RegisterUser(
//     string username,
//     string email,
//     string password,
//     string phoneNumber,
//     string role,
//     DateTime dateOfBirth,
//     char gender,
//     string country,
//     string bio,
//     byte[] profilePicture)
// {
//     var messageParameter = new NpgsqlParameter("message", NpgsqlDbType.Text)
//     {
//         Direction = ParameterDirection.Output
//     };

//     var userIdParameter = new NpgsqlParameter("user_id", NpgsqlDbType.Uuid)
//     {
//         Direction = ParameterDirection.Output
//     };

//     // Execute the SQL query with proper output parameter specification
//     Database.ExecuteSqlRaw("SELECT payments.register_user(" +
//         "@p0, @p1, @p2, @p3, @p4, @p5::timestamp with time zone, @p6, @p7, @p8, @p9)",
//         username, email, password, phoneNumber, role, dateOfBirth, gender, country, bio, profilePicture);

//     // Get the values of the OUT parameters
//     var message = messageParameter.Value as string;
//     var userId = userIdParameter.Value as Guid?;

//     return new RegisterUserResult
//     {
//         Message = message,
//         UserId = userId
//     };
// }


    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.UserId).HasName("users_pkey");

            entity.ToTable("users", "payments");

            entity.HasIndex(e => e.Email, "users_email_key").IsUnique();

            entity.Property(e => e.UserId)
                .HasDefaultValueSql("gen_random_uuid()")
                .HasColumnName("user_id");
            entity.Property(e => e.Email)
                .HasMaxLength(100)
                .HasColumnName("email");
            entity.Property(e => e.Password)
                .HasMaxLength(100)
                .HasColumnName("password");
            entity.Property(e => e.PhoneNumber)
                .HasMaxLength(15)
                .HasColumnName("phone_number");
            entity.Property(e => e.RoleId).HasColumnName("role_id");
            entity.Property(e => e.Username)
                .HasMaxLength(50)
                .HasColumnName("username");

            entity.HasOne(d => d.Role).WithMany(p => p.Users)
                .HasForeignKey(d => d.RoleId)
                .HasConstraintName("users_role_id_fkey");
        });

        modelBuilder.Entity<UserProfile>(entity =>
        {
            entity.HasKey(e => e.UserId).HasName("user_profiles_pkey");

            entity.ToTable("user_profiles", "payments");

            entity.Property(e => e.UserId)
                .HasDefaultValueSql("gen_random_uuid()")
                .HasColumnName("user_id");
            entity.Property(e => e.Bio).HasColumnName("bio");
            entity.Property(e => e.Country)
                .HasMaxLength(50)
                .HasColumnName("country");
            entity.Property(e => e.DateOfBirth).HasColumnName("date_of_birth");
            entity.Property(e => e.Gender)
                .HasMaxLength(1)
                .HasColumnName("gender");
            entity.Property(e => e.ProfilePicture).HasColumnName("profile_picture");

            entity.HasOne(d => d.User).WithOne(p => p.UserProfile)
                .HasForeignKey<UserProfile>(d => d.UserId)
                .HasConstraintName("user_profiles_user_id_fkey");
        });

        modelBuilder.Entity<UserRole>(entity =>
        {
            entity.HasKey(e => e.RoleId).HasName("user_roles_pkey");

            entity.ToTable("user_roles", "payments");

            entity.Property(e => e.RoleId)
                .HasDefaultValueSql("gen_random_uuid()")
                .HasColumnName("role_id");
            entity.Property(e => e.RoleName)
                .HasMaxLength(50)
                .HasColumnName("role_name");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
}
