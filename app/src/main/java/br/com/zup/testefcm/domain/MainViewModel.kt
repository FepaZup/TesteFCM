package br.com.zup.testefcm.domain

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import br.com.zup.testefcm.data.Notification
import br.com.zup.testefcm.domain.MyFirebaseCloudService.Companion.CURRENT_TOKEN
import br.com.zup.testefcm.domain.MyFirebaseCloudService.Companion.NEW_NOTIFICATION
import br.com.zup.testefcm.domain.MyFirebaseCloudService.Companion.NOTIFICATION_BODY_KEY
import br.com.zup.testefcm.domain.MyFirebaseCloudService.Companion.NOTIFICATION_TITLE_KEY
import br.com.zup.testefcm.domain.MyFirebaseCloudService.Companion.TOKEN_KEY

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var _currentToken = MutableLiveData<String>()
    var currentToken : LiveData<String> = _currentToken

    private var _lastNotification = MutableLiveData<Notification>()
    var lastNotification : LiveData<Notification> = _lastNotification

    private var context = application

    private var tokenReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                _currentToken.value = intent?.getStringExtra(TOKEN_KEY).toString()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    private var notificationReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                val title = intent?.getStringExtra(NOTIFICATION_TITLE_KEY).toString()
                val body = intent?.getStringExtra(NOTIFICATION_BODY_KEY).toString()
                val notification = Notification(
                    title = title,
                    body = body
                )
                _lastNotification.value = notification
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    init {
        LocalBroadcastManager.getInstance(context).registerReceiver((tokenReceiver),
            IntentFilter(CURRENT_TOKEN)
        )
        LocalBroadcastManager.getInstance(context).registerReceiver(
            (notificationReceiver),
            IntentFilter(NEW_NOTIFICATION)
        )
    }
}