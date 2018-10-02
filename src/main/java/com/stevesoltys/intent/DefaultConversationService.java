package com.stevesoltys.intent;

import com.stevesoltys.intent.builder.ConversationBuilderScriptScanner;
import com.stevesoltys.intent.classifier.DefaultIntentClassifier;
import com.stevesoltys.intent.classifier.DefaultIntentClassifierConfiguration;
import com.stevesoltys.intent.entity.EntityTag;
import com.stevesoltys.intent.model.IntentEntry;
import com.stevesoltys.intent.model.Message;
import com.stevesoltys.intent.model.Response;
import com.stevesoltys.intent.model.Session;
import com.stevesoltys.intent.repository.ConversationIntentRepository;

import java.util.*;

/**
 * @author Steve Soltys
 */
public class DefaultConversationService implements ConversationService {

    private final ConversationIntentRepository intentRepository;

    private final IntentClassifier intentClassifier;

    public DefaultConversationService(Set<IntentEntry> entries) {
        intentRepository = new ConversationIntentRepository();
        entries.forEach(intentRepository::register);

        DefaultIntentClassifierConfiguration configuration = DefaultIntentClassifierConfiguration.builder()
                .intentRepository(intentRepository)
                .build();

        intentClassifier = new DefaultIntentClassifier(configuration);
    }

    public DefaultConversationService(ConversationIntentRepository intentRepository,
                                      IntentClassifier intentClassifier) {
        this.intentRepository = intentRepository;
        this.intentClassifier = intentClassifier;
    }

    public DefaultConversationService() {
        this(new ConversationBuilderScriptScanner().scanClasspath());
    }

    @Override
    public Response query(Session session, String input) {
        IntentClassification intentClassification = intentClassifier.classify(session.getIntentContext(), input);
        List<Message> messages = new LinkedList<>();

        Optional.of(intentClassification.getIntent()).ifPresent(intent -> {
            IntentEntry intentEntry = intentRepository.getEntry(intent);
            session.getIntentContext().getContextIdentifiers().removeAll(intent.getContext().getContextIdentifiers());

            if (intentEntry != null) {
                Map<String, EntityTag> entityTagMap = new HashMap<>();

                intentClassification.getEntities().forEach(entityTag ->
                        entityTagMap.put(entityTag.getIdentifier(), entityTag));

                messages.addAll(intentEntry.getAction().apply(session, entityTagMap));
            }
        });

        return new Response(intentClassification, messages);
    }
}
