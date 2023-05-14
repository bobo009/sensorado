package sk.uniza.fri.sensorado.navigation

/**
 * Creates screen route to navigate to.
 * @param route route of the screen
 */
sealed class Screen(
    val route: String
) {
    object Overview : Screen(
        route = "screen_overview"
    )

    object Cameras : Screen(
        route = "screen_cameras"
    )

    object CameraDetail : Screen(
        route = "screen_camera_detail"
    )

    object Sensors : Screen(
        route = "screen_sensors"
    )

    object SensorDetail : Screen(
        route = "screen_sensor_detail"
    )
}