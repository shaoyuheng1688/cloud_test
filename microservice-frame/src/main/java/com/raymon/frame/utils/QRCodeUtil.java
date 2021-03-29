package com.raymon.frame.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
	
    
  //二维码颜色  
  private static final int BLACK = 0xFF000000;  
  //二维码颜色  
  private static final int WHITE = 0xFFFFFFFF;  

  /** 
   * <span style="font-size:18px;font-weight:blod;">ZXing 方式生成二维码</span> 
   * @param text    <a href="javascript:void();">二维码内容</a> 
   * @param width    二维码宽 
   * @param height    二维码高 
   * @param outPutPath    二维码生成保存路径 
   * @param imageType        二维码生成格式 
 * @throws IOException 
 * @throws WriterException 
   */  
  public static void zxingCodeCreate(String text, int width, int height, String outPutPath, String imageType) throws IOException, WriterException{  
      Map<EncodeHintType, Object> his = new HashMap<EncodeHintType, Object>();  
      //设置编码字符集  
      his.put(EncodeHintType.CHARACTER_SET, "utf-8");  
      his.put(EncodeHintType.MARGIN, 1);  
      //1、生成二维码  
      BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);  
      encode = updateBit(encode,2);//删除白边
      //2、获取二维码宽高  
      int codeWidth = encode.getWidth();  
      int codeHeight = encode.getHeight();  
        
      //3、将二维码放入缓冲流  
      BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);  
      for (int i = 0; i < codeWidth; i++) {  
          for (int j = 0; j < codeHeight; j++) {  
              //4、循环将二维码内容定入图片  
              image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);  
          }  
      }  
      File outPutImage = new File(outPutPath);  
      //如果图片不存在创建图片  
      if(!outPutImage.exists())  
          outPutImage.createNewFile();  
      //5、将二维码写入图片  
      ImageIO.write(image, imageType, outPutImage);  
     
  }

    /**
     * 生成二维码(base64)
     *
     * @param text       二维码内容
     * @param width      二维码宽
     * @param height     二维码高
     * @throws WriterException
     * @return Base64Str
     */
    public static String zxingCodeCreate(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> his = new HashMap<EncodeHintType, Object>();
        //设置编码字符集
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        his.put(EncodeHintType.MARGIN, 1);
        //1、生成二维码
        BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);
        encode = updateBit(encode, 2);//删除白边
        //2、获取二维码宽高
        int codeWidth = encode.getWidth();
        int codeHeight = encode.getHeight();

        //3、将二维码放入缓冲流
        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < codeWidth; i++) {
            for (int j = 0; j < codeHeight; j++) {
                //4、循环将二维码内容定入图片
                image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
            }
        }
        ByteArrayOutputStream bs = null;
        try {
            bs = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bs);//将绘制得图片输出到流
            String imgsrc = DatatypeConverter.printBase64Binary(bs.toByteArray());
            return imgsrc;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                bs = null;
            }
        }
        return null;
    }
    
  private static BitMatrix updateBit(BitMatrix matrix, int margin){
      int tempM = margin*2;
     int[] rec = matrix.getEnclosingRectangle();   //获取二维码图案的属性
          int resWidth = rec[2] + tempM;
          int resHeight = rec[3] + tempM;
          BitMatrix resMatrix = new BitMatrix(resWidth, resHeight); // 按照自定义边框生成新的BitMatrix
          resMatrix.clear();
      for(int i= margin; i < resWidth- margin; i++){   //循环，将二维码图案绘制到新的bitMatrix中
          for(int j=margin; j < resHeight-margin; j++){
              if(matrix.get(i-margin + rec[0], j-margin + rec[1])){
                  resMatrix.set(i,j);
              }
          }
      }
       return resMatrix;
  }
  
  
}
