package com.example.examensarbete.Cache

import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.GlobalVariables.UserGamesList


class LruCache<A :Any, B: Any> (val maxSize: Int) {

    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()


    private val internalCache: MutableMap<String, String> = object : LinkedHashMap<String, String>(0, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, String>?): Boolean {
            return size > maxSize
        }
    }


    fun put(key: String, gameObject: String) {
        internalCache.put(key, gameObject)
    }

    fun delete(key: String): Boolean {
        return internalCache.remove(key) != null
    }

    fun get (key: String): String? {
        return internalCache.get(key)
    }

    fun reset() {
        internalCache.clear()
    }

    fun size(): Long {
        return synchronized(this) {
            val snapshot = LinkedHashMap(internalCache)
            snapshot.size.toLong()
        }
    }

    fun dump () {
        println("!!! cache dump: $internalCache")
    }

}