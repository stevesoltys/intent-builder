package com.stevesoltys.intent.model;

import com.stevesoltys.intent.IntentContext;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.*;

/**
 * @author Steve Soltys
 */
public class Session {

    @Getter
    private IntentContext intentContext = new IntentContext(new LinkedList<>());

    @Accessors(fluent = true)
    @Getter
    private Map<String, Serializable> attributes = new HashMap<>();

    @Accessors(fluent = true)
    @Getter
    private List<Message> messages;

    public void setContext(String... context) {
        intentContext.getContextIdentifiers().clear();
        intentContext.getContextIdentifiers().addAll(Arrays.asList(context));
    }

    public void addContext(String... context) {
        intentContext.getContextIdentifiers().addAll(Arrays.asList(context));
    }

    public void removeContext(String... context) {
        intentContext.getContextIdentifiers().removeAll(Arrays.asList(context));
    }
}
