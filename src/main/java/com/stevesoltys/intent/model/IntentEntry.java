package com.stevesoltys.intent.model;

import com.stevesoltys.intent.Intent;
import com.stevesoltys.intent.entity.EntityTag;
import kotlin.jvm.functions.Function2;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Steve Soltys
 */
public class IntentEntry {

    @Getter
    private final Intent intent;

    @Getter
    private final BiFunction<Session, Map<String, EntityTag>, List<Message>> action;

    public IntentEntry(Intent intent, Function2<Session, Map<String, EntityTag>, List<Message>> action) {
        this.intent = intent;
        this.action = action::invoke;
    }

    public IntentEntry(Intent intent, BiFunction<Session, Map<String, EntityTag>, List<Message>> action) {
        this.intent = intent;
        this.action = action;
    }
}
