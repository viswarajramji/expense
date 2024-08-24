package com.demo.expense.kafka;
import com.demo.expense.Command;
import com.demo.expense.CommandExecutor;
import com.demo.expense.CommandExecutorFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private final CommandExecutorFactory commandExecutorFactory;
    private final ObjectMapper objectMapper;

    public KafkaConsumer(CommandExecutorFactory commandExecutorFactory, ObjectMapper objectMapper) {
        this.commandExecutorFactory = commandExecutorFactory;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${kafka.default.topic}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void consume(String message) {
        try {
            // Parse the JSON string into a JsonNode
            JsonNode node = objectMapper.readTree(message);

            // Extract the commandType and payload
            String commandType = node.get("commandType").asText();
            JsonNode payload = node.get("payload");

            // Modify the commandType to replace the service name with "budget"
            String regex = "(com\\.demo\\.)([a-zA-Z]+)(\\.command\\.)";
            String modifiedCommandType = commandType.replaceAll(regex, "$1expense$3");

            // Dynamically load the class based on the modified commandType
            Class<? extends Command> commandClass = (Class<? extends Command>) Class.forName(modifiedCommandType);

            // Deserialize the payload to the appropriate Command subclass
            Command command = objectMapper.treeToValue(payload, commandClass);

            // Get the appropriate CommandExecutor and execute the command
            CommandExecutor executor = commandExecutorFactory.getExecutor(command.getClass());
            executor.execute(command);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
