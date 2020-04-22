package com.example.doma

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.alamkanak.weekview.WeekViewEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class ScheduleActivityBasic : ScheduleActivityBase() {

    private lateinit var auth: FirebaseAuth
    private var events: MutableList<WeekViewEvent> = ArrayList()
    var thisYear = 0
    private var thisMonth = 0

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        /*var realEvent : WeekViewEvent = WeekViewEvent()
        var temp: MutableList<WeekViewEvent> = ArrayList()

        val firebaseDatabase = FirebaseDatabase.getInstance();
        val reference = firebaseDatabase.getReference()
        reference.child("events").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    var event = it.getValue(CustomEvent::class.java)
                    val start = Calendar.getInstance()
                    var startDate = Date(event!!.getStartTimeLong()!!)
                    start.time = startDate
                    start.set(event!!.getStartTimeStamp()!!.year, event.getStartTimeStamp()!!.month,
                        event!!.getStartTimeStamp()!!.day, event.getStartTimeStamp()!!.hours, event!!.getStartTimeStamp()!!.minutes)
                    val end = Calendar.getInstance()
                    var endDate = Date(event!!.getEndTimeLong()!!)
                    end.time = endDate
                    end.set(event!!.getEndTimeStamp()!!.year, event.getEndTimeStamp()!!.month,
                    event!!.getEndTimeStamp()!!.day, event.getEndTimeStamp()!!.hours, event!!.getEndTimeStamp()!!.minutes)
                    event.startTime = start
                    event.endTime = end
                    realEvent = event
                    temp.add(realEvent)
                }
                WeekView.adapter.notifyDataSetChanged()
                    events = temp
            }
        })*/


        val add: View = findViewById(R.id.addEvent)
        add.setOnClickListener {

//region Initializing Values
            val dialogBuilder: AlertDialog = AlertDialog.Builder(this).create()
            val inflater2 = this.layoutInflater
            val dialogView: View = inflater2.inflate(R.layout.popup_scedule, null)
            dialogBuilder.setView(dialogView)
            dialogBuilder.show()

            val btnBook = dialogView.findViewById<Button>(R.id.btnBook)
            val tbStart = dialogView.findViewById<TextView>(R.id.tbStart)
            tbStart.showSoftInputOnFocus = false
            val tbEnd = dialogView.findViewById<TextView>(R.id.tbEnd)
            tbEnd.showSoftInputOnFocus = false
            var startBool = false
            var endBool = false
            var startT = 0
            var endT = 0
            var date = 0
            val tbDate: Spinner = dialogView.findViewById<Spinner>(R.id.date_spinner)
            ArrayAdapter.createFromResource(this, R.array.date_array, R.layout.spinner_item).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                tbDate.adapter = adapter
            }
//endregion


            tbDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    val cal = Calendar.getInstance()
                    date = cal.get(Calendar.DAY_OF_MONTH)
                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val cal = Calendar.getInstance()
                    date = cal.get(Calendar.DAY_OF_MONTH) + position
                }
            }
            tbStart.setOnClickListener{
                var cal = Calendar.getInstance()
                val timeSetListener = OnTimeSetListener { view, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    tbStart.text = SimpleDateFormat("HH:mm").format(cal.time)

                        startT = cal.get(Calendar.HOUR_OF_DAY)*60 + cal.get(Calendar.MINUTE)
                }
                TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

                startBool = true
            }
            tbEnd.setOnClickListener{
                var cal = Calendar.getInstance()
                val timeSetListener = OnTimeSetListener { view, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)

                    tbEnd.text = SimpleDateFormat("HH:mm").format(cal.time)

                    endT = cal.get(Calendar.HOUR_OF_DAY)*60 + cal.get(Calendar.MINUTE)
                }
                TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

                endBool = true
            }

            btnBook.setOnClickListener {

              if ( startBool && endBool && startT+180 >= endT && startT < endT)
              {
                    createEvent(date, startT, endT)
                    dialogBuilder.dismiss()
                }
            }
        }
    }

    private fun monthFromDate(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH)
    }

    private fun createEvent(date: Int, start: Int, end: Int){
       if(checkEventTime(date, start, end) && start+180 >= end && start < end) {
            var startTime = Calendar.getInstance()
           var endTime = Calendar.getInstance()

           var startLong: Long
           var endLong: Long
           /*var startTimestamp : java.sql.Timestamp
           var endTimestamp : java.sql.Timestamp*/

            startTime.set(thisYear, thisMonth - 1, date, start, 0)
            endTime.set(thisYear, thisMonth - 1, date, end, 0)
           /*startTimestamp = java.sql.Timestamp(thisYear, thisMonth - 1, date, start, 0, 0, 0)
           endTimestamp = java.sql.Timestamp(thisYear, thisMonth - 1, date, end, 0, 0, 0)*/
            var hour : Int = start/60
            var minute = start - hour*60
            startTime.set(thisYear, thisMonth - 1, date, hour, minute)

           hour = end/60
           minute = end - hour*60
            endTime.set(thisYear, thisMonth - 1, date, hour, minute)

           startLong = startTime.timeInMillis / 1000;
           endLong = endTime.timeInMillis / 1000;

           var event = CustomEvent(11, auth.currentUser!!.email.toString(), startTime, endTime)
            event.id = events.size.toLong()
            event.startTime = startTime
            event.endTime = endTime
            event.name = auth.currentUser!!.email
            event.color = R.color.event_color_01
           event.setStartTimeLong(startLong)
           event.setEndTimeLong(endLong)
          /* event.setStartTimeStamp(startTimestamp)
           event.setEndTimeStamp(endTimestamp)*/
            events.add(event)

            var firebaseDatabase = FirebaseDatabase.getInstance()
            var databaseReference = firebaseDatabase.reference
            databaseReference.child("events").push().setValue(event)

            GetWeekView()?.notifyDatasetChanged()
        }
    }

    private fun checkEventTime(date: Int, start: Int, end: Int): Boolean{
        events.forEach {
        var otherStart = it.startTime.get(Calendar.HOUR_OF_DAY)*60 + it.startTime.get(Calendar.MINUTE)
        var otherEnd = it.endTime.get(Calendar.HOUR_OF_DAY)*60 + it.endTime.get(Calendar.MINUTE)
        var otherDate = it.startTime.get(Calendar.DAY_OF_MONTH)

            if (date == otherDate &&
                !(start >= otherEnd || end <= otherStart))
            {
                return false
            }
        }
        return true
    }

    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        thisMonth = newMonth - 1
        thisYear = newYear
        //while (events.isEmpty()) {

            /*       val firebaseDatabase = FirebaseDatabase.getInstance();
        val reference = firebaseDatabase.getReference()
        reference.child("events").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children

                children.forEach {
                    var event = it.getValue(CustomEvent::class.java)

                    val start = Calendar.getInstance()
                    start.set(event!!.getStartTimeStamp()!!.year, event.getStartTimeStamp()!!.month,
                        event!!.getStartTimeStamp()!!.day, event.getStartTimeStamp()!!.hours, event!!.getStartTimeStamp()!!.minutes)

                    val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
                    start.time = sdf.parse(event!!.getStartString())

                    val end = Calendar.getInstance()
                    end.set(event!!.getEndTimeStamp()!!.year, event.getEndTimeStamp()!!.month,
                        event!!.getEndTimeStamp()!!.day, event.getEndTimeStamp()!!.hours, event!!.getEndTimeStamp()!!.minutes)

                    val edf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
                    end.time = edf.parse(event!!.getEndString())

                    event.startTime = start
                    event.endTime = end
                    realEvent = event
                    events.add(realEvent)
                }
                GetWeekView()?.notifyDatasetChanged()
            }
        })*/
            //}
        if (monthFromDate() == thisMonth) {
            return events
        }
        return emptyList()
    }
}
//region Events
            //CreateEvent(date, startT, endT)
            /*if (monthFromDate() == thisMonth)
            {

                var realEvent : WeekViewEvent = WeekViewEvent()
                var temp: MutableList<WeekViewEvent> = ArrayList()

                    val firebaseDatabase = FirebaseDatabase.getInstance();
                    val reference = firebaseDatabase.getReference()
                    reference.child("events").addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {
                            // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val children = dataSnapshot.children

                            children.forEach {
                                var event = it.getValue(CustomEvent::class.java)
                                val start = Calendar.getInstance()
                                var startDate = Date(event!!.getStartTimeLong()!!)
                                start.time = startDate
                                *//*start.set(event!!.getStartTimeStamp()!!.year, event.getStartTimeStamp()!!.month,
                                            event!!.getStartTimeStamp()!!.day, event.getStartTimeStamp()!!.hours, event!!.getStartTimeStamp()!!.minutes)*//*
                                val end = Calendar.getInstance()
                                var endDate = Date(event!!.getEndTimeLong()!!)
                                end.time = endDate
                                *//*end.set(event!!.getEndTimeStamp()!!.year, event.getEndTimeStamp()!!.month,
                                        event!!.getEndTimeStamp()!!.day, event.getEndTimeStamp()!!.hours, event!!.getEndTimeStamp()!!.minutes)*//*
                                event.startTime = start
                                event.endTime = end
                                realEvent = event
                                events.add(realEvent)
                            }
                            *//*WeekView.adapter.notifyDataSetChanged()*//*
                        }
                    })
                return events
            }
            //events = temp

            *//*var startTime = Calendar.getInstance()
            startTime[Calendar.HOUR_OF_DAY] = 3
            startTime[Calendar.MINUTE] = 0
            startTime[Calendar.MONTH] = newMonth - 1
            startTime[Calendar.YEAR] = 2020
            var endTime = startTime.clone() as Calendar
            endTime.add(Calendar.HOUR, 1)
            endTime[Calendar.MONTH] = newMonth - 1
            var eventi = WeekViewEvent(1, "You", startTime, endTime)
            eventi.color = R.color.event_color_02
            events.add(eventi)

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

            eventi = WeekViewEvent(2, "User66", startTime, endTime)
            eventi.color = R.color.event_color_02
            events.add(eventi)*//*
        }
        return emptyList()
        //endregion*/
