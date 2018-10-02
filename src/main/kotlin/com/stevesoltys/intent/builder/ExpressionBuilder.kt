package com.stevesoltys.intent.builder

import com.stevesoltys.intent.expression.Expression

/**
 * @author Steve Soltys
 */
open class ExpressionBuilder {

    private val expressionBuilder = Expression.builder()

    fun build(): Expression {
        return expressionBuilder.build()
    }
}