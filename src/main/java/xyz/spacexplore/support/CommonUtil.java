package xyz.spacexplore.support;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class CommonUtil {
    /**
     * 
     * @doc:获得具体的stackTraceInfo在程序中
     * @author Andreby
     * @date 2017年12月22日 上午8:40:40
     * @param e
     * @return
     */
    public static String getStackTraceInfo(Exception e) {

        StringWriter sw = null;
        PrintWriter pw = null;

        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);// 将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();

            return sw.toString();
        } catch (Exception ex) {

            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }

    }
}
