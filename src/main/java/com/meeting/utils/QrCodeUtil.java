package com.meeting.utils;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class QrCodeUtil {


    public static void main(String[] args) {
        String path = "\\app\\file\\qrCode\\";
        File file = new File(path);
        if (file.exists()) {
            file.mkdir();
        }
        String fileName = "1.jpg";
        String content = "hello world";

        QrCodeUtil.encode(content, path + fileName);
    }


    public static void encode(String str, String path) {
        try {
            BitMatrix byteMatrix;
            //将文字转换成二维矩阵，并设置矩阵大小，这里的矩阵大小就是后面生成的图片像素大小
            byteMatrix = new MultiFormatWriter().encode(new String(str.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8), BarcodeFormat.QR_CODE, 800, 800);
            File file = new File(path);
            //将矩阵文件转换成图片文件
            MatrixToImageWriter.writeToFile(byteMatrix, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
