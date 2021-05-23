package com.example.examensarbete.Cache

import com.example.examensarbete.DataClasses.CurrentGame


class LruCache<A :Any, B: Any> (val maxSize: Int) {

    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()


    private val internalCache: MutableMap<String, CurrentGame> = object : LinkedHashMap<String, CurrentGame>(0, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<String, CurrentGame>?): Boolean {
            return size > maxSize
        }
    }


    fun put(key: String, gameObject: CurrentGame) {
        internalCache.put(key, gameObject)
    }

    fun delete(key: String): Boolean {
        return internalCache.remove(key) != null
    }

    fun get (key: String, obj: CurrentGame): CurrentGame? {
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