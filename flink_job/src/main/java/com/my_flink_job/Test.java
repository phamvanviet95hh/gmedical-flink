package com.my_flink_job;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;

public class Test {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> stream = env.readTextFile("input/data.txt");
        SingleOutputStreamOperator<Tuple2<String, Long>> wordCounts = stream.flatMap(new FlatMapFunction<String, Tuple2<String, Long>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Long>> collector) throws Exception {
                Arrays.stream(s.split("\\s+")).forEach(word -> {
                    if (!word.isEmpty()) {
                        collector.collect(new Tuple2<>(word, 1L));
                    }
                });
            }
        });

        KeyedStream<Tuple2<String, Long>, String> keyedStream = wordCounts.keyBy(tuple -> tuple.f0);
        SingleOutputStreamOperator<Tuple2<String, Long>> summedStream = keyedStream.sum(1);
        summedStream.print();
        env.execute("Word Count Example");
    }

}
