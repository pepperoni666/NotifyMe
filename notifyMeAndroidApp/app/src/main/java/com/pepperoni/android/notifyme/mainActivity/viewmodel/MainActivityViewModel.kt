package com.pepperoni.android.notifyme.mainActivity.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.pepperoni.android.notifyme.FirestoreRepository
import com.pepperoni.android.notifyme.models.Topic

class MainActivityViewModel: ViewModel() {

    var firebaseRepository = FirestoreRepository()
    var subscribedTopics : MutableLiveData<List<Topic>> = MutableLiveData()

    fun subscribeTopic(newTopic: Topic){
        firebaseRepository.subscribeToTopic(newTopic).addOnFailureListener {
            Log.d("MainActivityViewModel","Failed to subscribe to topic ${newTopic.topic_name}!")
        }
    }

    // get realtime updates from firebase regarding subscribed topics
    fun getSubscribedTopics(): LiveData<List<Topic>> {
        firebaseRepository.getSubscribedTopics().addSnapshotListener(EventListener<QuerySnapshot> { value, e ->
            if (e != null) {
                Log.w("MainActivityViewModel", "Listen failed.", e)
                subscribedTopics.value = null
                return@EventListener
            }

            var subscribedTopicsList : MutableList<Topic> = mutableListOf()
            for (doc in value!!) {
                var topic = doc.toObject(Topic::class.java)
                subscribedTopicsList.add(topic)
            }
            subscribedTopics.value = subscribedTopicsList
        })

        return subscribedTopics
    }

    fun unsubscribeTopic(topic: Topic){
        firebaseRepository.unsubscribeTopic(topic).addOnFailureListener {
            Log.e("MainActivityViewModel","Failed to unsubscribe topic ${topic.topic_name}!")
        }
    }
}