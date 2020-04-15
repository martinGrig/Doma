package com.example.doma

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import com.alamkanak.weekview.WeekViewEvent
import com.google.firebase.database.FirebaseDatabase
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.alamkanak.weekview.WeekView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_schedule.*
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

            btnBook.setOnClickListener {
                //events.clear()
                val tbDate = dialogView.findViewById<TextView>(R.id.tbDate).text.toString()
                val date = Integer.parseInt(tbDate)
                val tbStart = dialogView.findViewById<TextView>(R.id.tbStart).text.toString()
                val start = Integer.parseInt(tbStart)
                val tbEnd = dialogView.findViewById<TextView>(R.id.tbEnd).text.toString()
                val end = Integer.parseInt(tbEnd)

                CreateEvent(date, start, end)
                dialogBuilder.dismiss()
            }
        }

        val firebaseDatabase = FirebaseDatabase.getInstance();
        val reference = firebaseDatabase.getReference()
        reference.child("event").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    var event = it.getValue(CustomEvent::class.java)
                    events.add(event!!)
                }
                /*WeekView.adapter.notifyDataSetChanged()*/
            }
        })

    }
    private fun monthFromDate(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH)
    }

    private fun CreateEvent(date: Int, start: Int, end: Int){
       if(CheckEventTime(date, start, end) && start+2 >= end) {
            var startTime = Calendar.getInstance()
            var endTime = Calendar.getInstance()

            startTime.set(thisYear, thisMonth - 1, date, start, 0)
            endTime.set(thisYear, thisMonth - 1, date, end, 0)

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
        }
    }

    private fun CheckEventTime(date: Int, start: Int, end: Int): Boolean{
        events.forEach {
        var otherStart = it.startTime.get(Calendar.HOUR_OF_DAY)*60 + it.startTime.get(Calendar.MINUTE)
        var otherEnd = it.endTime.get(Calendar.HOUR_OF_DAY)*60 + it.endTime.get(Calendar.MINUTE)
        var otherDate = it.startTime.get(Calendar.DAY_OF_MONTH)

            if (date == otherDate &&
                !(start*60 >= otherEnd || end*60 <= otherStart))
            {
                return false
            }
        }
        return true
    }

    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        thisMonth = newMonth -1
        thisYear = newYear

        if (monthFromDate() == thisMonth)
        {
            //Replace below code so that it loads events from the database (first of course clean up database)
            //Make it in a function so we can use it elsewhere

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
        }
        return emptyList()
        //endregion
    }
}





 /*   override fun onMonthChange(
        newYear: Int,
        newMonth: Int
    ): List<WeekViewEvent> {

        val events: MutableList<WeekViewEvent> =
            ArrayList()

=======
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
}*/
