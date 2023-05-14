package sk.uniza.fri.sensorado.additional

/**
 * Camel case regex
 */
val camelCaseRegex = "((?<!^)[A-Z](?![A-Z]))(?=\\S)".toRegex()