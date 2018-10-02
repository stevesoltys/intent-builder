package com.stevesoltys.intent.repository;

import com.stevesoltys.intent.Conversation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Steve Soltys
 */
@AllArgsConstructor
@NoArgsConstructor
public class ConversationRepository {

    @Getter
    private Set<Conversation> conversations = new HashSet<>();
}
