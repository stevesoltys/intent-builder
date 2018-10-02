package com.stevesoltys.intent;

import com.stevesoltys.intent.model.Response;
import com.stevesoltys.intent.model.Session;

/**
 * @author Steve Soltys
 */
public interface ConversationService {

    Response query(Session session, String input);
}
