package com.stevesoltys.intent;

import com.stevesoltys.intent.model.IntentEntry;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Steve Soltys
 */
public class Conversation {

    @Getter
    private final String identifier;

    @Getter
    private final Set<IntentEntry> entries;

    public Conversation(String identifier, Set<IntentEntry> entries) {
        this.identifier = identifier;
        this.entries = entries;
    }

    public Conversation(String identifier) {
        this(identifier, new HashSet<>());
    }
}
