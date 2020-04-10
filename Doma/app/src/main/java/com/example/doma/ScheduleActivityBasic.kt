package com.example.doma

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekViewEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_schedule.*
import java.util.*

class ScheduleActivityBasic : ScheduleActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val add: View = findViewById(R.id.addEvent)
                add.setOnClickListener { view ->
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show()
            }
        }

    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        val events: MutableList<WeekViewEvent> = ArrayList()

        //region Events
        var startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 3
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        var endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR, 1)
        endTime[Calendar.MONTH] = newMonth - 1
        var event = WeekViewEvent(1, "User 56", startTime, endTime)
        event.color = R.color.event_color_01
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 6
        startTime[Calendar.MINUTE] = 30
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear

        endTime = startTime.clone() as Calendar
        endTime[Calendar.HOUR_OF_DAY] = 8
        endTime[Calendar.MINUTE] = 30
        endTime[Calendar.MONTH] = newMonth - 1
        //startTime.add(Calendar.DATE, 1)

        event = WeekViewEvent(2, "User66", startTime, endTime)
        event.color = R.color.event_color_02
        events.add(event)

        return events
        //endregion
    }
}
/*
    override fun onMonthChange(
        newYear: Int,
        newMonth: Int
    ): List<WeekViewEvent> {

        val events: MutableList<WeekViewEvent> =
            ArrayList()



        startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 4
        startTime[Calendar.MINUTE] = 20
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime[Calendar.HOUR_OF_DAY] = 5
        endTime[Calendar.MINUTE] = 0
        event = WeekViewEvent(10, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_03)
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 5
        startTime[Calendar.MINUTE] = 30
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 2)
        endTime[Calendar.MONTH] = newMonth - 1
        event = WeekViewEvent(2, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_02)
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 5
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        startTime.add(Calendar.DATE, 1)
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        endTime[Calendar.MONTH] = newMonth - 1
        event = WeekViewEvent(3, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_03)
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.DAY_OF_MONTH] = 15
        startTime[Calendar.HOUR_OF_DAY] = 3
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event = WeekViewEvent(4, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_04)
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.DAY_OF_MONTH] = 1
        startTime[Calendar.HOUR_OF_DAY] = 3
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event = WeekViewEvent(5, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_01)
        events.add(event)

        startTime = Calendar.getInstance()
        startTime[Calendar.DAY_OF_MONTH] = startTime.getActualMaximum(Calendar.DAY_OF_MONTH)
        startTime[Calendar.HOUR_OF_DAY] = 15
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 3)
        event = WeekViewEvent(5, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_02)
        events.add(event)

        //AllDay event
        startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 0
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR_OF_DAY, 23)
        event = WeekViewEvent(7, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_04)
        events.add(event)
        events.add(event)
        startTime = Calendar.getInstance()
        startTime[Calendar.DAY_OF_MONTH] = 8
        startTime[Calendar.HOUR_OF_DAY] = 2
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime[Calendar.DAY_OF_MONTH] = 10
        endTime[Calendar.HOUR_OF_DAY] = 23
        event = WeekViewEvent(8, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_03)
        events.add(event)

        // All day event until 00:00 next day
        startTime = Calendar.getInstance()
        startTime[Calendar.DAY_OF_MONTH] = 10
        startTime[Calendar.HOUR_OF_DAY] = 0
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.SECOND] = 0
        startTime[Calendar.MILLISECOND] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = newYear
        endTime = startTime.clone() as Calendar
        endTime[Calendar.DAY_OF_MONTH] = 11
        event = WeekViewEvent(8, getEventTitle(startTime), startTime, endTime)
        event.color = resources.getColor(R.color.event_color_01)
        events.add(event)
        return events
    }
      override fun onFirstVisibleDayChanged(
           newFirstVisibleDay: Calendar?,
           oldFirstVisibleDay: Calendar
       ) {
           if (newFirstVisibleDay != oldFirstVisibleDay)
           {
               Toast.makeText(this, "Not Today", Toast.LENGTH_SHORT).show()
           }
       }*/
