package com.stevesoltys.intent.builder

import com.stevesoltys.intent.Conversation
import com.stevesoltys.intent.model.IntentEntry
import java.util.*
import kotlin.collections.HashSet
import kotlin.script.templates.ScriptTemplateDefinition

/**
 * @author Steve Soltys
 */
@ScriptTemplateDefinition(scriptFilePattern = ".*\\.kts")
open class ConversationBuilderScript {

    var conversations = HashSet<Conversation>()

    var intents = HashSet<IntentEntry>()

    fun conversation(identifier: String, init: ConversationBuilder.() -> Unit) {
        val builder = ConversationBuilder()
        builder.identifier(identifier)
        builder.init()

        conversations.add(builder.build())
    }

    fun intent(id: String, context: String? = null, init: IntentBuilder.() -> Unit) {
        val builder = IntentBuilder(id, context)
        builder.init()

        intents.add(builder.build())
    }
}

open class ConversationBuilder {

    private var identifier = UUID.randomUUID().toString()

    private var intents = HashSet<IntentEntry>()

    fun identifier(identifier: String) {
        this.identifier = identifier
    }

    fun intent(id: String, context: String? = null, init: IntentBuilder.() -> Unit) {
        val builder = IntentBuilder(id, context)
        builder.init()

        intents.add(builder.build())
    }

    fun build(): Conversation {
        return Conversation(identifier, intents)
    }
}