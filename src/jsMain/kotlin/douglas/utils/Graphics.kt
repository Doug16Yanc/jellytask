package douglas.utils

import douglas.utils.external.Chart
import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement
import kotlin.js.json

var identityChartInstance: dynamic = null
var priorityChartInstance: dynamic = null
var statusChartInstance: dynamic = null

fun renderIdentityChart(polyps: Int, jellyfishs: Int) {
    val ctx = (document.getElementById("identityChart") as HTMLCanvasElement).getContext("2d")

    identityChartInstance?.destroy()

    identityChartInstance=  Chart(ctx, json(
        "type" to "pie",
        "data" to json(
            "labels" to arrayOf("Pólipos", "Medusas"),
            "datasets" to arrayOf(json(
                "label" to "Identidade dos Membros",
                "data" to arrayOf(polyps, jellyfishs),
                "backgroundColor" to arrayOf("#08031f", "#290a59")
            ))
        ),
        "options" to json(
            "plugins" to json(
                "title" to json(
                    "display" to true,
                    "text" to "Gráfico de Identidade dos Membros",
                    "color" to "#fff",
                    "font" to json(
                        "size" to 20,
                        "weight" to "bold",
                        "family" to "Arial"
                    ),
                    "padding" to json(
                        "top" to 10,
                        "bottom" to 30
                    )
                ),
                "legend" to json(
                    "position" to "bottom",
                    "labels" to json(
                        "padding" to 20,
                        "color" to "#fff",
                        "font" to json(
                            "size" to 16,
                            "weight" to "bold",
                            "family" to "Arial"
                        )
                    )
                ),
                "tooltip" to json(
                    "bodyFont" to json(
                        "family" to "Arial",
                        "size" to 14,
                        "weight" to "bold"
                    ),
                    "titleFont" to json(
                        "family" to "Arial",
                        "size" to 16,
                        "weight" to "bold"
                    )
                )
            )
        )
    ))
}


fun renderPriorityChart(low: Int, medium: Int, high: Int, critical: Int) {
    val ctx = (document.getElementById("priorityChart") as HTMLCanvasElement).getContext("2d")

    priorityChartInstance?.destroy()

    priorityChartInstance = Chart(ctx, json(
        "type" to "pie",
        "data" to json(
            "labels" to arrayOf("Baixa", "Média", "Alta", "Crítica"),
            "datasets" to arrayOf(json(
                "label" to "Prioridade das Tarefas",
                "data" to arrayOf(low, medium, high, critical),
                "backgroundColor" to arrayOf("#08031f", "#290a59", "#3a0ca3", "#290a63")
            ))
        ),
        "options" to json(
            "plugins" to json(
                "title" to json(
                    "display" to true,
                    "text" to "Gráfico de prioridade das tarefas",
                    "color" to "#fff",
                    "font" to json(
                        "size" to 20,
                        "weight" to "bold",
                        "family" to "Arial"
                    ),
                    "padding" to json(
                        "top" to 10,
                        "bottom" to 30
                    )
                ),
                "legend" to json(
                    "position" to "bottom",
                    "labels" to json(
                        "padding" to 16,
                        "color" to "#fff",
                        "font" to json(
                            "size" to 20,
                            "weight" to "bold",
                            "family" to "Arial"
                        )
                    ),
                ),
                "tooltip" to json(
                    "bodyFont" to json(
                        "family" to "Arial",
                        "size" to 14,
                        "weight" to "bold"
                    ),
                    "titleFont" to json(
                        "family" to "Arial",
                        "size" to 16,
                        "weight" to "bold"
                    )
                )
            )
        )
    ))
}


fun renderStatusChart(pending: Int, current: Int, finished: Int) {
    val ctx = (document.getElementById("statusChart") as HTMLCanvasElement).getContext("2d")

    statusChartInstance?.destroy()

    statusChartInstance = Chart(ctx, json(
        "type" to "pie",
        "data" to json(
            "labels" to arrayOf("Em fila", "Em andamento", "Concluída"),
            "datasets" to arrayOf(json(
                "label" to "Prioridade das Tarefas",
                "data" to arrayOf(pending, current, finished),
                "backgroundColor" to arrayOf("#08031f", "#290a59", "#3a0ca3")
            ))
        ),
        "options" to json(
            "plugins" to json(
                "title" to json(
                    "display" to true,
                    "text" to "Gráfico de status das tarefas",
                    "color" to "#fff",
                    "font" to json(
                        "size" to 20,
                        "weight" to "bold",
                        "family" to "Arial"
                    ),
                    "padding" to json(
                        "top" to 10,
                        "bottom" to 30
                    )
                ),
                "legend" to json(
                    "position" to "bottom",
                    "labels" to json(
                        "padding" to 16,
                        "color" to "#fff",
                        "font" to json(
                            "size" to 20,
                            "weight" to "bold",
                            "family" to "Arial"
                        )
                    ),
                ),
                "tooltip" to json(
                    "bodyFont" to json(
                        "family" to "Arial",
                        "size" to 14,
                        "weight" to "bold"
                    ),
                    "titleFont" to json(
                        "family" to "Arial",
                        "size" to 16,
                        "weight" to "bold"
                    )
                )
            )
        )
    ))
}



