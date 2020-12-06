package nio.bio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @Classname BioDemo1
 * @Date 2020/11/9 0:30
 * @Autor lengxuezhang
 */
public class BioDemo1 {
    public static void main(String[] args) throws Exception {
        // 1.创建 FileInputStream，并指定文件路径
        FileInputStream fis = new FileInputStream("G:\\背球接力.jpg");
        // 2.读取文件
        // 2.1 单个字节读取
      /*  int data = 0;
        while((data = fis.read()) != -1) {
            System.out.println(data);
        }*/
        // 2.2 一次读取多个字节
        byte[] buf = new byte[1024];
        int count = 0;
        while((count = fis.read(buf)) != -1) {
            System.out.println(new String(buf,0,count));
        }

        // 3. 关闭
        fis.close();
    }
}
