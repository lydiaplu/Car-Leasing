package com.lydiaplullc.CarLeasing.utils;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ConvertPicture {
    public static byte[] convertSvgToPng(byte[] fileBytes) {
        try {
            // 将byte[]转换为UTF-8编码的String
            String svgContent = new String(fileBytes, StandardCharsets.UTF_8);
            // 将byte[]转换为InputStream
            InputStream inputStream = new ByteArrayInputStream(svgContent.getBytes(StandardCharsets.UTF_8));
            // 准备PNG输出流
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();

            // 创建一个PNG转码器实例
            PNGTranscoder transcoder = new PNGTranscoder();

            // 设置转码输入和输出
            TranscoderInput input = new TranscoderInput(inputStream);
            TranscoderOutput output = new TranscoderOutput(pngOutputStream);

            // 执行转码
            transcoder.transcode(input, output);

            // 关闭流
            inputStream.close();
            pngOutputStream.close();

            return pngOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
