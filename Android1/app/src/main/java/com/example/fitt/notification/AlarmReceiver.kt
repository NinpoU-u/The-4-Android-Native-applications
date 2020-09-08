package com.example.fitt.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.fitt.repository.ReminderLocalRepository
import com.example.fitt.utils.KEY_ID
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

//Alarm receiver is Broadcast, implementing onReceive fun which one is activated when it get message from system.
//and execute code in fun onReceive()
class AlarmReceiver : BroadcastReceiver() {
    private val TAG = AlarmReceiver::class.java.simpleName
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive() called with: context = [$context], intent = [$intent]")
        if (context != null && intent != null) {
            if (intent.extras != null) {
                val reminderData = ReminderLocalRepository(context).getReminderById(intent.extras!!.getLong(KEY_ID))
                if (reminderData != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationHelper.createNotificationForWorkout(context, reminderData)
                    } else {
                        NotificationHelper.createNotificationForOldDevices(context, reminderData)
                    }

                }
            }
        }
    }
}