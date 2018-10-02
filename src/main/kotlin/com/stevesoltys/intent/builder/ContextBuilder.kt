package com.stevesoltys.intent.builder

import com.stevesoltys.intent.IntentContext

/**
 * @author Steve Soltys
 */
open class ContextBuilder {

    private val contextBuilder = IntentContext.builder()

    fun identifier(contextIdentifier: String) {
        contextBuilder.contextIdentifier(contextIdentifier)
    }

    fun build(): IntentContext {
        return contextBuilder.build()
    }
}