package douglas.viewmodel

import douglas.model.enums.Priority
import douglas.model.enums.Status
import douglas.service.DashboardService
import douglas.utils.renderIdentityChart
import douglas.utils.renderPriorityChart
import douglas.utils.renderStatusChart
import kotlinx.browser.document

class DashboardViewModel{

    private val dashboardService = DashboardService()

    fun renderDashboard() {
        val polyps = dashboardService.countPolyps()
        val jellyfishs = dashboardService.countJellyfishs()

        val low = dashboardService.countByPriority(Priority.BAIXA)
        val medium = dashboardService.countByPriority(Priority.MÉDIA)
        val high = dashboardService.countByPriority(Priority.ALTA)
        val critical = dashboardService.countByPriority(Priority.CRÍTICA)

        val pending = dashboardService.countByStatus(Status.EM_FILA)
        val current = dashboardService.countByStatus(Status.EM_ANDAMENTO)
        val finished = dashboardService.countByStatus(Status.CONCLUÍDA)

        val dashboard = document.getElementById("dashboard")!!
        dashboard.innerHTML = """
            <canvas id="identityChart" width="300" height="300"></canvas>
            <canvas id="priorityChart" width="300" height="300"></canvas>
            <canvas id="statusChart" width="300" height="300"></canvas>
        """.trimIndent()

        renderIdentityChart(polyps, jellyfishs)
        renderPriorityChart(low, medium, high, critical)
        renderStatusChart(pending, current, finished)
    }
}
