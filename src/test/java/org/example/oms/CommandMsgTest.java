package org.example.oms;

import org.example.common.model.msg.CommandMessage;
import org.example.common.model.msg.CommandMessage_commandType;
import org.example.common.model.msg.QuoteRequestCreateCmd;
import org.junit.jupiter.api.Test;

public class CommandMsgTest {

    @Test
    void testCreateCommandMessage() {
        // Create a CommandMessage instance
        CommandMessage commandMessage = CommandMessage.newBuilder()
        .setCommandType(CommandMessage_commandType.QuoteRequestCreateCmd)
        // .setCommand(QuoteRequestCreateCmd.newBuilder()
        //     .build())
        .build();
        System.out.println("CommandMessage created: " + commandMessage);
    }
}
