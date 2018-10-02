package com.stevesoltys.intent.builder;

import com.stevesoltys.intent.Conversation;
import com.stevesoltys.intent.Intent;
import com.stevesoltys.intent.IntentFactory;
import com.stevesoltys.intent.expression.DefaultExpressionFactory;
import com.stevesoltys.intent.factory.DefaultIntentFactory;
import com.stevesoltys.intent.model.IntentEntry;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

import java.util.*;

/**
 * @author Steve Soltys
 */
public class ConversationBuilderScriptScanner {

    private final IntentFactory intentFactory;

    public ConversationBuilderScriptScanner(IntentFactory intentFactory) {
        this.intentFactory = intentFactory;
    }

    public ConversationBuilderScriptScanner() {
        intentFactory = new DefaultIntentFactory(new DefaultExpressionFactory());
    }

    public Set<IntentEntry> scanClasspath() {
        return scanClasspath(new FastClasspathScanner());
    }

    public Set<IntentEntry> scanClasspath(FastClasspathScanner scanner) {
        List<ConversationBuilderScript> conversationScripts = new ArrayList<>();
        List<Class<? extends ConversationBuilderScript>> scriptClasses = new ArrayList<>();

        scanner.matchSubclassesOf(ConversationBuilderScript.class, scriptClasses::add).scan();

        try {
            for (Class<? extends ConversationBuilderScript> pluginClass : scriptClasses) {
                conversationScripts.add(pluginClass.getConstructor().newInstance());
            }

        } catch (Exception e) {
            throw new ConversationBuilderScanException(e);
        }

        Set<IntentEntry> intents = new HashSet<>();
        Map<String, Conversation> conversations = new HashMap<>();

        conversationScripts.forEach(script -> {

            script.getIntents().forEach(entry -> {
                Intent intent = entry.getIntent();
                Intent createdIntent = intentFactory.create(intent.getIdentifier(), intent.getExpressions(),
                        intent.getContext().getContextIdentifiers());

                if (!intents.add(new IntentEntry(createdIntent, entry.getAction()))) {
                    throw new ConversationBuilderScanException("Duplicate intent '" + intent.getIdentifier() + "'");
                }
            });

            script.getConversations().forEach(conversation -> {
                Conversation result = conversations.getOrDefault(conversation.getIdentifier(),
                        new Conversation(conversation.getIdentifier()));

                conversation.getEntries().forEach(entry -> {
                    Intent intent = entry.getIntent();
                    Intent createdIntent = intentFactory.create(intent.getIdentifier(), intent.getExpressions(),
                            intent.getContext().getContextIdentifiers());

                    if (!result.getEntries().add(new IntentEntry(createdIntent, entry.getAction()))) {
                        throw new ConversationBuilderScanException("Duplicate intent '" + intent.getIdentifier() +
                                "' found in conversation '" + conversation.getIdentifier() + "'");
                    }
                });

                conversations.put(result.getIdentifier(), result);
            });
        });

        conversations.values().forEach(conversation -> intents.addAll(conversation.getEntries()));
        return intents;
    }
}
