package com.stevesoltys.intent.builder;

/**
 * @author Steve Soltys
 */
public class ConversationBuilderScanException extends RuntimeException {

    public ConversationBuilderScanException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConversationBuilderScanException(String message) {
        super(message);
    }

    public ConversationBuilderScanException(Throwable throwable) {
        super(throwable);
    }
}
