using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Diagnostics.HealthChecks;
using System.Threading.Tasks;

namespace authentication_api.Controllers
{
    [Route("api/health")]
    [ApiController]
    public class HealthCheckController : ControllerBase
    {
        private readonly HealthCheckService _healthCheckService;

        public HealthCheckController(HealthCheckService healthCheckService)
        {
            _healthCheckService = healthCheckService;
        }

        [HttpGet]
        public async Task<IActionResult> CheckHealth()
        {
            var healthReport = await _healthCheckService.CheckHealthAsync();
            
            if (healthReport.Status == HealthStatus.Healthy)
            {
                return Ok(healthReport);
            }
            else
            {
                return StatusCode(500, healthReport);
            }
        }
    }
}
