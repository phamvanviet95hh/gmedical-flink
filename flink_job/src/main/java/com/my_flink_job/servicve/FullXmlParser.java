package com.my_flink_job.servicve;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.my_flink_job.dtos.FileContentDto;
import com.my_flink_job.dtos.FileContentGdDto;
import com.my_flink_job.dtos.GiamDinhHs;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.util.Collector;

public class FullXmlParser extends RichFlatMapFunction<FileContentDto, FileContentGdDto> {
    XmlMapper xmlMapper = new XmlMapper();

    ObjectMapper jsonMapper = new ObjectMapper(); // để parse JSON
    @Override
    public void open(Configuration parameters) throws Exception {

    }

    @Override
    public void flatMap(FileContentDto json, Collector<FileContentGdDto> out) throws Exception {
        String data = null;
        String pathFile = null;
        String nameFile = null;
        GiamDinhHs giamDinhHs = null;
        // Bước 2: lấy nội dung XML từ content
        data = json.getContent();
        pathFile = json.getPathFile();
        nameFile = json.getNameFile();
        try {

            // Bước 3: parse XML sang GiamDinhHs
            giamDinhHs = xmlMapper.readValue(data, GiamDinhHs.class);
            // Bước 4: emit kết quả
            out.collect(FileContentGdDto.builder()
                    .fileName(nameFile)
                    .pathFile(pathFile)
                    .content(giamDinhHs)
                    .build());

        } catch (Exception e) {
//            System.err.println("Lỗi khi parse chuỗi JSON: " + json.getContent());
            out.collect(FileContentGdDto.builder()
                    .fileName(nameFile)
                    .pathFile(pathFile)
                    .content(null)
                    .build());
            e.printStackTrace(); // log lỗi đầy đủ
        }



    }

}
