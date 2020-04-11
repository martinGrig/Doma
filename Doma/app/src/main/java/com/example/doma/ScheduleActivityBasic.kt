package com.example.doma

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import com.alamkanak.weekview.WeekViewEvent
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class ScheduleActivityBasic : ScheduleActivityBase() {

    private val events: MutableList<WeekViewEvent> = ArrayList()
    var thisYear = 0
    private var thisMonth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val add: View = findViewById(R.id.addEvent)
        add.setOnClickListener {


            val dialogBuilder: AlertDialog = AlertDialog.Builder(this).create()
            val inflater2 = this.layoutInflater
            val dialogView: View = inflater2.inflate(R.layout.popup_scedule, null)
            dialogBuilder.setView(dialogView)
            dialogBuilder.show()


            val btnBook = dialogView.findViewById<Button>(R.id.btnBook)
            val buttonPopup = dialogView.findViewById<Button>(R.id.btnCancel)

            btnBook.setOnClickListener{
                //events.clear()

                val tbDate = dialogView.findViewById<TextView>(R.id.tbDate).text.toString()
                val date = Integer.parseInt(tbDate)
                val tbStart = dialogView.findViewById<TextView>(R.id.tbStart).text.toString()
                val start = Integer.parseInt(tbStart)

                var startTime = Calendar.getInstance()
                var endTime = Calendar.getInstance()

                startTime.set(thisYear, thisMonth-1, date, start, 0)
                endTime.set(thisYear, thisMonth-1, date, start+2, 0)


                var event = CustomEvent(11, "WASHER", startTime, endTime)
                event.id = events.size.toLong()
                event.startTime = startTime
                event.endTime = endTime
                event.name = "Washer"
                event.color = R.color.event_color_01
                events.add(event)

                var firebaseDatabase = FirebaseDatabase.getInstance()
                var databaseReference = firebaseDatabase.getReference()
                databaseReference.child("events").push().setValue(event)

                GetWeekView()?.notifyDatasetChanged()
                dialogBuilder.dismiss()
            }

            buttonPopup.setOnClickListener {
                dialogBuilder.dismiss()
            }
        }
    }


    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        var cal = Calendar.getInstance()
        if (cal.get(Calendar.MONTH) + 1 == newMonth) {

            thisMonth = newMonth
            thisYear = newYear

            var startTime = Calendar.getInstance()
            var endTime = Calendar.getInstance()

            startTime.set(thisYear, newMonth-1, 13, 6, 0)
            endTime.set(thisYear, newMonth-1, 13, 8, 0)

            var event = WeekViewEvent(events.size.toLong(), "You", startTime, endTime)
            event.color = R.color.event_color_01
            events.add(event)

            startTime.set(thisYear, thisMonth-1, 11, 1, 0)
            endTime.set(thisYear, thisMonth-1, 11, 4, 0)

            event = WeekViewEvent(events.size.toLong(), "User66", startTime, endTime)
            event.color = R.color.event_color_03
            events.add(event)

            return events
        }
        return emptyList()
    }
}
