package com.example.doma

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.alamkanak.weekview.WeekViewEvent
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_schedule.*
import java.util.*


class ScheduleActivityBasic : ScheduleActivityBase() {

    private val events: MutableList<WeekViewEvent> = ArrayList()
    var thisYear = 0
    private var thisMonth = 0

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val add: View = findViewById(R.id.addEvent)
        add.setOnClickListener {

//region Popup Initialize
            // Initialize a new layout inflater instance
            val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            // Inflate a custom view using layout inflater
            val view = inflater.inflate(R.layout.popup_scedule,null)

            // Initialize a new instance of popup window
            val popupWindow = PopupWindow(
                view, // Custom view to show in popup window
                LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                LinearLayout.LayoutParams.WRAP_CONTENT // Window height
            )

            // Set an elevation for the popup window
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                popupWindow.elevation = 10.0F
            }


            // If API level 23 or higher then execute the code
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                // Create a new slide animation for popup window enter transition
                val slideIn = Slide()
                slideIn.slideEdge = Gravity.TOP
                popupWindow.enterTransition = slideIn

                // Slide animation for popup window exit transition
                val slideOut = Slide()
                slideOut.slideEdge = Gravity.RIGHT
                popupWindow.exitTransition = slideOut
            }
            // Set a click listener for popup's button widget
            val buttonPopup = view.findViewById<Button>(R.id.btnCancel)
            buttonPopup.setOnClickListener {
                // Dismiss the popup window
                popupWindow.dismiss()
            }
//endregion

            val tbDate = view.findViewById<TextView>(R.id.tbDate).text.toString()
            val date = Integer.parseInt(tbDate)
            val tbStart = view.findViewById<TextView>(R.id.tbStart).text.toString()
            val start = Integer.parseInt(tbStart)
            val btnBook = view.findViewById<Button>(R.id.btnBook)

            btnBook.setOnClickListener{
                //events.clear()

                /*var startTime = Calendar.getInstance()
                startTime[Calendar.HOUR_OF_DAY] = start
                startTime[Calendar.MINUTE] = 0
                startTime[Calendar.MONTH] = thisMonth - 1
                startTime[Calendar.YEAR] = thisYear
                var endTime = startTime.clone() as Calendar
                endTime.add(Calendar.HOUR, 1)
                endTime[Calendar.MONTH] =  thisMonth - 1*/

                var startTime = Calendar.getInstance()
                var endTime = Calendar.getInstance()

                startTime.set(thisYear, thisMonth-1, 12, 2, 0)
                endTime.set(thisYear, thisMonth-1, 12, 3, 0)
                var event = CustomEvent(11, "WASHERE", startTime, endTime)
                event.id = 12
                event.startTime = startTime
                event.endTime = endTime
                event.name = "Washer"
                event.color = R.color.event_color_01
                events.add(event)

                var firebaseDatabase = FirebaseDatabase.getInstance()
                var databaseReference = firebaseDatabase.getReference()
                databaseReference.child("events").push().setValue(event)

                //onMonthChange(thisYear, thisMonth)
                popupWindow.dismiss()
            }

            // Finally, show the popup window on app
            TransitionManager.beginDelayedTransition(schedule)
            popupWindow.showAtLocation(
                weekView, // Location to display popup window
                Gravity.CENTER, // Exact position of layout to display popup
                0, // X offset
                0 // Y offset
            )
        }
    }


    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        thisMonth = newMonth
        thisYear = newYear

        //events.clear()
        //region Events
        var startTime = Calendar.getInstance()
        startTime[Calendar.HOUR_OF_DAY] = 3
        startTime[Calendar.MINUTE] = 0
        startTime[Calendar.MONTH] = newMonth - 1
        startTime[Calendar.YEAR] = 2020
        var endTime = startTime.clone() as Calendar
        endTime.add(Calendar.HOUR, 1)
        endTime[Calendar.MONTH] = newMonth - 1
        var event = WeekViewEvent(1, "You", startTime, endTime)
        event.color = R.color.event_color_02
        events.add(event)
/*
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
        events.add(event)*/

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
