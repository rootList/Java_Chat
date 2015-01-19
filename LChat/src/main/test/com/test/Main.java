package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class Main {
public static void main(String[] args) {

     BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter bufw = new BufferedWriter(new OutputStreamWriter(System.out));
     System.out.println("请输入任意字母并按回车键：");
     String line =null;
     try {
		while((line=bufr.readLine())!=null)    {
		     if("over".equals(line))            //判断输入over，就结束循环
		         break;
		     bufw.write(line.toUpperCase());  
		     bufw.newLine();                    //换行 
		     bufw.flush();                      //刷新
		    }
		  bufw.close();                          //关闭 
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
