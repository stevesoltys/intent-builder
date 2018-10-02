package com.stevesoltys.intent.builder

import com.stevesoltys.intent.Intent
import com.stevesoltys.intent.IntentContext
import com.stevesoltys.intent.entity.EntityTag
import com.stevesoltys.intent.expression.Expression
import com.stevesoltys.intent.expression.token.Token
import com.stevesoltys.intent.model.IntentEntry
import com.stevesoltys.intent.model.Message
import com.stevesoltys.intent.model.Session
import java.util.*

/**
 * @author Steve Soltys
 */
open class IntentBuilder(id: String = UUID.randomUUID().toString(),
                         context: String? = null) {

    private val intentBuilder = Intent.builder().identifier(id)

    private var action: (Session, Map<String, EntityTag>) -> List<Message> = { _, _ -> arrayListOf() }

    init {
        if (context != null) {
            intentBuilder.context(IntentContext.builder()
                    .contextIdentifier(context)
                    .build())
        }
    }

    open fun context(contextIdentifier: String) {
        intentBuilder.context(IntentContext.builder()
                .contextIdentifier(contextIdentifier)
                .build())
    }

    open fun context(init: ContextBuilder.() -> Unit) {
        val builder = ContextBuilder()
        builder.init()
        intentBuilder.context(builder.build())
    }

    open fun expression(vararg tokens: Any) {
        val mappedTokens = arrayListOf<Token>()

        tokens.forEach {
            when (it) {
                is Token -> mappedTokens.add(it)
                is String -> mappedTokens.addAll(Expression(it).tokens)
                else -> throw IllegalArgumentException("Invalid expression token")
            }
        }

        intentBuilder.expression(Expression(mappedTokens))
    }

    open fun expression(expression: Expression) {
        intentBuilder.expression(expression)
    }

    open fun action(action: (session: Session, entities: Map<String, EntityTag>) -> Any) {
        this.action = { session, entities -> getActionResult(action.invoke(session, entities)) }
    }

    open fun action(action: (session: Session) -> Any) {
        this.action = { session, _ -> getActionResult(action.invoke(session)) }
    }

    private fun getActionResult(actionResult: Any): List<Message> {
        val result = arrayListOf<Message>()

        if (actionResult is List<*>) {
            actionResult.filter { it is Message }.forEach {
                result.add(it as Message)
            }
        }

        return result
    }

    open fun build(): IntentEntry {
        return IntentEntry(intentBuilder.build(), action)
    }
}