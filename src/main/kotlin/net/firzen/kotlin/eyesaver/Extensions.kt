package net.firzen.kotlin.eyesaver

import org.json.JSONObject
import java.util.concurrent.TimeUnit

fun JSONObject.getIntSafe(name: String) : Int? {
    try {
        return if(has(name)) {
            getInt(name)
        }
        else {
            null
        }
    }
    catch (e: Exception) {
        return null
    }
}

fun String.runCommand() {
    println("Running command: \"$this\"")
    ProcessBuilder(*split(" ").toTypedArray())
        .redirectOutput(ProcessBuilder.Redirect.INHERIT)
        .redirectError(ProcessBuilder.Redirect.INHERIT)
        .start()
        .waitFor(60, TimeUnit.MINUTES)
}

/**
 * Runs given action while ignoring all exceptions.
 *
 * @param action () -> Unit
 */
fun ignoreAllExceptions(debug: Boolean = false, action: () -> Unit) {
    withoutExceptionsOrNull(debug, action)
}

/**
 * Runs given action. If the action executes without an exception, its value is returned.
 * If there ois any exception during the call, null is returned instead.
 *
 * @param action () -> T?
 * @return T?
 */
fun <T> withoutExceptionsOrNull(debug: Boolean = false, action: () -> T?) : T? {
    return withoutExceptionsOrValue(null, debug, action)
}

/**
 * Runs given action. If the action executes without an exception, its value is returned.
 * If there ois any exception during the call, returnIfFail value is returned instead.
 *
 * @param action () -> T?
 * @return T?
 */
fun <T> withoutExceptionsOrValue(returnIfFail: T, debug: Boolean = false, action: () -> T) : T {
    try {
        return action()
    }
    catch (e: Throwable) {
        if(debug) {
            e.printStackTrace()
        }
        return returnIfFail
    }
}
