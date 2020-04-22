package com.example.doma

//import android.R
import android.graphics.RectF
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.alamkanak.weekview.DateTimeInterpreter
import com.alamkanak.weekview.MonthLoader.MonthChangeListener
import com.alamkanak.weekview.WeekView
import com.alamkanak.weekview.WeekView.*
import com.alamkanak.weekview.WeekViewEvent
import java.text.SimpleDateFormat
import java.util.*


abstract class ScheduleActivityBase : AppCompatActivity(), EventClickListener,
    MonthChangeListener, EventLongPressListener, EmptyViewLongPressListener {
    var weekView: WeekView? = null
        private set
    lateinit var drawer: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        // Get a reference for the week view in the layout.
        weekView = findViewById(R.id.weekView)

        // Show a toast message about the touched event.
        weekView!!.setOnEventClickListener(this)

        // The week view has infinite scrolling horizontally. We have to provide the events of a month every time the month changes on the week view.
        weekView!!.monthChangeListener = this

        // Set long press listener for events.
        weekView!!.eventLongPressListener = this

        // Set long press listener for empty view
        weekView!!.emptyViewLongPressListener = this

        var toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.schedule)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_drawer_navigation_drawer, menu)
        return true
    }
    //endregion

    //region Schedule Events

    private fun setupDateTimeInterpreter(shortDate: Boolean) {
        weekView!!.dateTimeInterpreter = object : DateTimeInterpreter {
            override fun interpretDate(date: Calendar): String {
                val weekdayNameFormat =
                    SimpleDateFormat("EEE", Locale.getDefault())
                var weekday = weekdayNameFormat.format(date.time)
                val format =
                    SimpleDateFormat(" M/d", Locale.getDefault())
                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate) weekday = weekday[0].toString()
                return weekday.toUpperCase() + format.format(date.time)
            }

            override fun interpretTime(hour: Int): String {
                return if (hour > 11) (hour - 12).toString() + " PM" else if (hour == 0) "12 AM" else "$hour AM"
            }
        }
    }

    override fun onEventClick(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(this, "Clicked " + event.name, Toast.LENGTH_SHORT).show()
    }

    override fun onEventLongPress(event: WeekViewEvent, eventRect: RectF) {
        Toast.makeText(this, "Long pressed event: " + event.name, Toast.LENGTH_SHORT).show()
    }

    override fun onEmptyViewLongPress(time: Calendar) {
        Toast.makeText(this, "Empty view long pressed: ", Toast.LENGTH_SHORT)
            .show()
    }

    companion object {
        private const val TYPE_THREE_DAY_VIEW = 2
    }

    fun GetWeekView(): WeekView? {
        return weekView
    }
    //endregion


}














