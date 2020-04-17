package com.example.doma

import com.alamkanak.weekview.WeekViewEvent
import com.google.firebase.database.Exclude
import java.security.Timestamp
import java.util.*

public class CustomEvent(

) : WeekViewEvent() {

    private var mId: Long = 0
    private var mStartTime: Calendar? = null
    private var mEndTime: Calendar? = null
    private var mStartTimeLong: Long? = null;
    private var mEndTimeLong: Long? = null;
    /*private var mStartTimeStamp: java.sql.Timestamp? = null*/
    private var mName: String? = null
    private var mLocation: String? = null
    /*private var mStartString: String? = null
    private var mEndString: String? = null
    private var mEndTimeStamp: java.sql.Timestamp? = null*/
    private var mColor = 0

    fun CustomEvent(){}
    constructor(i: Int,
                s: String,
                startTime: Calendar,
                endTime: Calendar) : this()  {

    }

    fun WeekViewEvent(
        id: Long,
        name: String?,
        startYear: Int,
        startMonth: Int,
        startDay: Int,
        startHour: Int,
        startMinute: Int,
        endYear: Int,
        endMonth: Int,
        endDay: Int,
        endHour: Int,
        endMinute: Int
    ) {
        this.mId = id
        this.mStartTime = Calendar.getInstance()
        this.mStartTime!!.set(Calendar.YEAR, startYear)
        this.mStartTime!!.set(Calendar.MONTH, startMonth - 1)
        this.mStartTime!!.set(Calendar.DAY_OF_MONTH, startDay)
        this.mStartTime!!.set(Calendar.HOUR_OF_DAY, startHour)
        this.mStartTime!!.set(Calendar.MINUTE, startMinute)
        this.mEndTime = Calendar.getInstance()
        this.mEndTime!!.set(Calendar.YEAR, endYear)
        this.mEndTime!!.set(Calendar.MONTH, endMonth - 1)
        this.mEndTime!!.set(Calendar.DAY_OF_MONTH, endDay)
        this.mEndTime!!.set(Calendar.HOUR_OF_DAY, endHour)
        this.mEndTime!!.set(Calendar.MINUTE, endMinute)
        this.mName = name
    }


    fun WeekViewEvent(
        id: Long,
        name: String?,
        location: String?,
        startTime: Calendar?,
        endTime: Calendar?
    ) {
        this.mId = id
        this.mName = name
        this.mLocation = location
        this.mStartTime = startTime
        this.mEndTime = endTime
    }

    fun WeekViewEvent(
        id: Long,
        name: String?,
        startTime: Calendar?,
        endTime: Calendar?
    ) {
        this(id, name, null, startTime, endTime)
    }



    private operator fun invoke(id: Long, name: String?, nothing: Nothing?, startTime: Calendar?, endTime: Calendar?) {

    }
    @Exclude
    override fun getStartTime(): Calendar? {
        return mStartTime
    }
    fun getStartTimeLong(): Long?{
        return mStartTimeLong
    }
    /*fun getStartTimeStamp(): java.sql.Timestamp? {
        return mStartTimeStamp
    }
    fun getStartString(): String? {
        return mStartTime.toString()
    }*/

    override fun setStartTime(startTime: Calendar?) {
        this.mStartTime = startTime
    }
    fun setStartTimeLong(startTime: Long?) {
        this.mStartTimeLong = startTime
    }
    /*fun setStartTimeStamp(startTime: java.sql.Timestamp?) {
        this.mStartTimeStamp = startTime
    }
    fun setStartString(startTime: String?) {
        this.mStartString = startTime
    }*/
    @Exclude
    override fun getEndTime(): Calendar? {
        return mEndTime
    }
    fun getEndTimeLong(): Long? {
        return mEndTimeLong
    }

    /*fun getEndTimeStamp(): java.sql.Timestamp? {
        return mEndTimeStamp
    }
    fun getEndString(): String? {
        return mEndTime.toString()
    }*/

    override fun setEndTime(endTime: Calendar?) {
        this.mEndTime = endTime
    }
     fun setEndTimeLong(endTime: Long?) {
        this.mEndTimeLong = endTime
    }

    /*fun setEndTimeStamp(endTime: java.sql.Timestamp?) {
        this.mEndTimeStamp = endTime
    }
    fun setEndString(endTime: String?) {
        this.mEndString = endTime
    }*/

    override fun getName(): String? {
        return mName
    }

    override fun setName(name: String?) {
        this.mName = name
    }

    override fun getColor(): Int {
        return mColor
    }

    override fun setColor(color: Int) {
        this.mColor = color
    }

    override fun getId(): Long {
        return mId
    }

    override fun setId(id: Long) {
        this.mId = id
    }
}