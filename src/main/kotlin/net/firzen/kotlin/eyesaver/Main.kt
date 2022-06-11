package net.firzen.kotlin.eyesaver

import org.joda.time.DateTime
import org.json.JSONObject
import java.io.File
import java.lang.Thread.sleep
import kotlin.math.abs

const val CONFIG_FILE = "/etc/eyesaver/config.json"

fun main() {
    while(true) {
        try {
            val tempsMap = loadTemperaturesMap()

            if (tempsMap == null) {
                System.err.println("Unable to load config file from $CONFIG_FILE!")
            }
            else {
                val currentTime = DateTime()
                val currentTemp = computeScreenTemperature(currentTime.hourOfDay, currentTime.minuteOfHour, tempsMap)

                setScreenTemperature(currentTemp)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        sleep(60000)
    }
}

/**
 * Loads screen temperature for each hour from configuration file.
 */
private fun loadTemperaturesMap() : Map<Int, Int>? {
    return loadTemperaturesMap(CONFIG_FILE)
}

/**
 * Loads screen temperature for each hour from JSON file.
 */
private fun loadTemperaturesMap(jsonFile: String) : Map<Int, Int>? {
    return withoutExceptionsOrNull {
        val rawConfig = File(jsonFile).readText()
        val json = JSONObject(rawConfig)

        val tempsMap = HashMap<Int, Int>()

        for (i in 0..23) {
            val temp = json.getIntSafe(i.toString())
            if (temp != null) {
                tempsMap[i] = temp
            }
        }

        tempsMap
    }
}

/**
 * Computes screen temperature using given time and temperatures hash map.
 *
 * @param hour Int (number 0-23)
 * @param minute Int (number 0-59)
 * @param tempsMap Map<Int, Int> --> Map<hour, temperature>
 * @return Int (temperature in kelvins)
 */
private fun computeScreenTemperature(hour: Int, minute: Int, tempsMap: Map<Int, Int>) : Int {
    val clock = Clock()
    val (previousHour, nextHour) = clock.findSiblingHours(hour)

    println("Current hour: $hour")
    println("Current minute: $minute")
    println("Between hours: $hour, $nextHour")

    val currentTemp = tempsMap.get(hour)!!
    val nextTemp = tempsMap.get(nextHour)!!
    val tempDifference = abs(currentTemp - nextTemp)

    println("Current temperature: $currentTemp")
    println("Next temperature: $nextTemp")

    val temperature = if(currentTemp < nextTemp) {
        currentTemp + (tempDifference / 60 * minute)
    }
    else {
        currentTemp - (tempDifference / 60 * minute)
    }

    println("Output temperature: $temperature")

    return temperature
}

private fun setScreenTemperature(temp: Int) {
    "redshift -P -O $temp".runCommand()
}
