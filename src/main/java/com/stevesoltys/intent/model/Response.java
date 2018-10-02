package com.stevesoltys.intent.model;

import com.stevesoltys.intent.IntentClassification;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * @author Steve Soltys
 */
@AllArgsConstructor
public class Response {

    @Getter
    private final IntentClassification intentClassification;

    @Getter
    private final List<Message> messages;
}
