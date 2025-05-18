package com.github.nnbros.rtp.pve.action;

import java.util.function.Consumer;
import java.util.function.Function;

public class ActionPipelines {

    public static <T> ActionPipeline create(Function<ActionContext, ActionResult<T>> actionProcessor,
                                            Consumer<ActionResult<T>> nextActionHandler) {

        return actionContext -> {
            ActionResult<T> result = actionProcessor.apply(actionContext);
            nextActionHandler.accept(result);
        };
    }

    public static <T> ActionPipeline create(Function<ActionContext, ActionResult<T>> actionProcessor,
                                            Consumer<ActionResult<T>> nextActionHandler,
                                            Consumer<ActionResult<T>> altNextActionHandler) {
        return (actionContext) -> {
            ActionResult<T> result = actionProcessor.apply(actionContext);
            if (result.isSuccessful()) {
                nextActionHandler.accept(result);
            } else {
                altNextActionHandler.accept(result);
            }
        };
    }
}
