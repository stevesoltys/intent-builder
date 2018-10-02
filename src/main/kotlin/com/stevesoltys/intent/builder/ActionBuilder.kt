package com.stevesoltys.intent.builder

import java.util.*
import kotlin.collections.HashMap

/**
 * @author Steve Soltys
 */
open class ActionBuilder {

    val responseList = LinkedList<String>()

    fun payload(init: Map<Any, Any>.() -> Unit) {
        val json = HashMap<Any, Any>()
        json.init()
        responseList.add(json.toString())
    }

    fun text(text : String) {
        responseList.add(text)
    }
}