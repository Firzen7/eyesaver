package net.firzen.kotlin.eyesaver

data class ClockHour(val hour: Int, var previousHour: Int? = null, var nextHour: Int? = null)

class Clock {
    private val clock: Map<Int, ClockHour>

    init {
        val newClock = hashMapOf<Int, ClockHour>()

        for (i in 0..23) {
            newClock[i] = ClockHour(i)
        }

        newClock[0]!!.previousHour = 23
        newClock[1]!!.previousHour = 0
        newClock[2]!!.previousHour = 1
        newClock[3]!!.previousHour = 2
        newClock[4]!!.previousHour = 3
        newClock[5]!!.previousHour = 4
        newClock[6]!!.previousHour = 5
        newClock[7]!!.previousHour = 6
        newClock[8]!!.previousHour = 7
        newClock[9]!!.previousHour = 8
        newClock[10]!!.previousHour = 9
        newClock[11]!!.previousHour = 10
        newClock[12]!!.previousHour = 11
        newClock[13]!!.previousHour = 12
        newClock[14]!!.previousHour = 13
        newClock[15]!!.previousHour = 14
        newClock[16]!!.previousHour = 15
        newClock[17]!!.previousHour = 16
        newClock[18]!!.previousHour = 17
        newClock[19]!!.previousHour = 18
        newClock[20]!!.previousHour = 19
        newClock[21]!!.previousHour = 20
        newClock[22]!!.previousHour = 21
        newClock[23]!!.previousHour = 22

        newClock[0]!!.nextHour = 1
        newClock[1]!!.nextHour = 2
        newClock[2]!!.nextHour = 3
        newClock[3]!!.nextHour = 4
        newClock[4]!!.nextHour = 5
        newClock[5]!!.nextHour = 6
        newClock[6]!!.nextHour = 7
        newClock[7]!!.nextHour = 8
        newClock[8]!!.nextHour = 9
        newClock[9]!!.nextHour = 10
        newClock[10]!!.nextHour = 11
        newClock[11]!!.nextHour = 12
        newClock[12]!!.nextHour = 13
        newClock[13]!!.nextHour = 14
        newClock[14]!!.nextHour = 15
        newClock[15]!!.nextHour = 16
        newClock[16]!!.nextHour = 17
        newClock[17]!!.nextHour = 18
        newClock[18]!!.nextHour = 19
        newClock[19]!!.nextHour = 20
        newClock[20]!!.nextHour = 21
        newClock[21]!!.nextHour = 22
        newClock[22]!!.nextHour = 23
        newClock[23]!!.nextHour = 0

        this.clock = newClock
    }

    fun findHour(hour: Int) : ClockHour {
        return clock[hour]!!
    }

    fun findSiblingHours(hour: Int) : Pair<Int, Int> {
        return findSiblingHours(findHour(hour))
    }

    fun findSiblingHours(hour: ClockHour) : Pair<Int, Int> {
        return Pair(hour.previousHour!!, hour.nextHour!!)
    }
}
