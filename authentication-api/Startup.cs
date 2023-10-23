using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.OpenApi.Models;
using authentication_api.Model;
using authentication_api.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.Text;

public class Startup
{
    public void ConfigureServices(IServiceCollection services)
    {
        services.AddScoped<IUserService, UserService>();
        services.AddScoped<IAuthService, AuthService>();
        services.AddControllers();
        services.AddHealthChecks();

        // Configure JWT Bearer authentication
        services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
            .AddJwtBearer(options =>
            {
                options.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuer = true,
                    ValidateAudience = true,
                    ValidIssuer = "your_issuer", // Replace with your JWT issuer
                    ValidAudience = "your_audience", // Replace with your JWT audience
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("your_security_key")) // Replace with your security key
                };
            });

        // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
        services.AddEndpointsApiExplorer();
        services.AddSwaggerGen(c =>
        {
            c.SwaggerDoc("v1", new OpenApiInfo
            {
                Title = "authentication-api", // Replace with your API's name
                Version = "v1"
            });
        });
    }

    public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
    {
        if (env.IsDevelopment())
        {
            app.UseSwagger();
            app.UseSwaggerUI(c =>
            {
                c.SwaggerEndpoint("/swagger/v1/swagger.json", "authentication-api"); // Replace "Your API Name" with your API's name
                c.RoutePrefix = "swagger"; // You can customize the URL route for Swagger UI
            });
        }

        app.UseHttpsRedirection();
        app.UseRouting();

        // Enable authentication
        app.UseAuthentication();
        app.UseAuthorization();

        app.UseEndpoints(endpoints =>
        {
            endpoints.MapControllers();
        });
    }
}
