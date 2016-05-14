package milindparikh;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Iterator;


import org.json.*;

import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer08;
import org.apache.flink.streaming.util.serialization.SimpleStringSchema;
import org.apache.flink.api.common.functions.MapFunction;

/*
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

*/


/**
 * Read Strings from Kafka and print them to standard out.
 * Note: On a cluster, DataStream.print() will print to the TaskManager's .out file!
 *
 * Please pass the following arguments to run the example:
 * 	--topic test --bootstrap.servers localhost:9092 --zookeeper.connect localhost:2181 --group.id myconsumer
 * 	--topic test --bootstrap.servers <ipaddr-forkafka>:9092 --zookeeper.connect <ipaddr-forzookeeper>:2181 --group.id myconsumer --cassandraHost <ipaddr-forcassandra> --elasticsearchHost <ipaddr-forelasticsearch>
 *
 */
public class ReadFromKafka {


    
	public static void main(String[] args) throws Exception {
		// parse input arguments
		final ParameterTool parameterTool = ParameterTool.fromArgs(args);


		if(parameterTool.getNumberOfParameters() < 6) {
			System.out.println("Missing parameters!\nUsage: Kafka --topic <topic> " +
					"--bootstrap.servers <kafka brokers> --zookeeper.connect <zk quorum> --group.id <some id> --cassandraHost <ipaddr-forcassandra> --elasticsearchHost <ipaddr-forelasticsearch>");
			return;
		}

		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.getConfig().disableSysoutLogging();
		env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(4, 10000));
		env.enableCheckpointing(5000); // create a checkpoint every 5 secodns
		env.getConfig().setGlobalJobParameters(parameterTool); // make parameters available in the web interface

		DataStream<String> messageStream = env
				.addSource(new FlinkKafkaConsumer08<>(
						parameterTool.getRequired("topic"),
						new SimpleStringSchema(),
						parameterTool.getProperties()));

		DataStream<JSONObject> jsonStream = messageStream.map(new ProcessMessage(parameterTool.getRequired("cassandraHost"), parameterTool.getRequired("elasticsearchHost")));
		
		
		    
		// write kafka stream to standard out.
		jsonStream.print();

		env.execute("Read from Kafka example");
	}



}
