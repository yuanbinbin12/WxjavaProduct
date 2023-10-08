package com.ybb.testController;

import com.ybb.pojo.noteMessage;
import com.ybb.service.messageNotificationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.data.redis.connection.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

@Controller
public class messageController {
    @Resource
    messageNotificationService mns;
    @ResponseBody
    @PostMapping(value = "/getNoteMessage",produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Object getNoteMessage(@RequestBody noteMessage message){
        System.out.println("post"+message);
        return mns.getNoteMessage(message);
    }
    @RequestMapping({"/","/index"})
    public String index(){
        return "login/index";
    }
    @GetMapping("/readPdfFileImg")
    public void readPdfFileImg(HttpServletResponse resp, String path) throws Exception {
        Assert.isTrue(StringUtils.isNotEmpty(path),"文件路径不能为空！");
        RestTemplate restTemplate = new RestTemplate();
        InputStream inputStream1 = getClass().getResourceAsStream("/config.txt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream1));
        byte[] bytes = new byte[10240];
        InputStream inputStream = new ByteArrayInputStream(bytes);
        // pdf转图片
        PDDocument doc = PDDocument.load(inputStream);
        PDFRenderer renderer = new PDFRenderer(doc);
        int pageCount = doc.getNumberOfPages();
        for (int i = 0; i < pageCount; i++) {
            // dpi，图片像素点，dpi越高图片体积越大，216很清晰，105体积稳定
            BufferedImage image = renderer.renderImageWithDPI(i, 216);
            // 格式为JPG
            ImageIO.write(image, "jpg", resp.getOutputStream());
        }
        String filename = path.substring(path.lastIndexOf("/")+1).replaceAll(".pdf",".jpg");
        resp.setContentType("image/jpeg;charset=UTF-8");
        resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
    }

}
