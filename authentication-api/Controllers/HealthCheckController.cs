using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Diagnostics.HealthChecks;

namespace authentication_api.Controllers
{
    [Route("api/health")]
    [ApiController]
    public class HealthCheckController : ControllerBase
    {
        private readonly HealthCheckService _healthCheckService;
        private readonly ILogger<HealthCheckController> _logger; // Add a logger

        public HealthCheckController(HealthCheckService healthCheckService, ILogger<HealthCheckController> logger)
        {
            _healthCheckService = healthCheckService;
            _logger = logger;
        }

        [HttpGet]
        public async Task<IActionResult> CheckHealth()
        {
            var healthReport = await _healthCheckService.CheckHealthAsync();

            if (healthReport.Status == HealthStatus.Healthy)
            {
                _logger.LogInformation("Health check is healthy.");
                return Ok(healthReport);
            }
            else
            {
                _logger.LogError("Health check is unhealthy.");
                return StatusCode(500, healthReport);
            }
        }
    }
}
