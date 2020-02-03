package com.pepperoni.android.notifyme

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.pepperoni.android.notifyme.models.Topic

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // save address to firebase
    fun subscribeToTopic(newTopic: Topic): Task<Void> {
        //var
        var documentReference = db.collection("receivers").document(FirebaseInstanceId.getInstance().id)
            .collection("subscribedTopics").document(newTopic.topic_name)
        return documentReference.set(newTopic)
    }

    // get saved addresses from firebase
    fun getSubscribedTopics(): CollectionReference {
        var collectionReference = db.collection("receivers/${FirebaseInstanceId.getInstance().id}/subscribedTopics")
        return collectionReference
    }

    fun unsubscribeTopic(topic: Topic): Task<Void> {
        var documentReference =  db.collection("receivers/${FirebaseInstanceId.getInstance().id}/subscribedTopics")
            .document(topic.topic_name)

        return documentReference.delete()
    }
}