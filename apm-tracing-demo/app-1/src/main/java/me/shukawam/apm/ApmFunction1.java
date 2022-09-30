package me.shukawam.apm;

import java.util.logging.Logger;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

public class ApmFunction1 {
    private static final Logger LOGGER = Logger.getLogger(ApmFunction1.class.getName());

    public String handleRequest(HTTPGatewayContext ctx) {
        loggingHttpGatewayContext(ctx);
        SleepService sleepService = new SleepService();
        return sleepService.greeting();
    }

    private void loggingHttpGatewayContext(HTTPGatewayContext ctx) {
        if (ctx == null) {
            LOGGER.info("HTTPGatewayContext is null.");
        } else {
            {
                LOGGER.info("*** HttpGatewayContext ***");
                LOGGER.info("RequestURL: " + ctx.getRequestURL());
                LOGGER.info("RequestMethod: " + ctx.getMethod());
                ctx.getHeaders().asMap().forEach((k, v) -> LOGGER.info(k + ": " + v));
                LOGGER.info("*** HttpGatewayContext ***");
                LOGGER.info("*** InvocationContext ***");
                LOGGER.info("AppID: " + ctx.getInvocationContext().getRuntimeContext().getAppID());
                LOGGER.info("AppName: " + ctx.getInvocationContext().getRuntimeContext().getAppName());
                LOGGER.info("FunctionID: " + ctx.getInvocationContext().getRuntimeContext().getFunctionID());
                LOGGER.info("FunctionName: " + ctx.getInvocationContext().getRuntimeContext().getFunctionName());
                ctx.getInvocationContext().getRuntimeContext().getInvokeInstance()
                        .ifPresent(i -> LOGGER.info(i.toString()));
                ctx.getInvocationContext().getRuntimeContext().getConfiguration()
                        .forEach((k, v) -> LOGGER.info(k + ": " + v));
                LOGGER.info("*** InvocationContext ***");
            }
        }
    }
}