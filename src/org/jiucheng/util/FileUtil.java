/**
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author jiucheng
 * @version 0.0.1(2014/08/22)
 * 
 */
public class FileUtil {

    /**
     * 遍历指定文件夹root下的所有符合regex条件名称的File
     * @param root
     * @param regex
     * @return
     */
    public static List<File> list(File root, final String regex) {
        final List<File> results = new ArrayList<File>();
        if (!root.exists() || root.isFile()) {
            return results;
        }
        root.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                if (pathname.isFile()) {
                    if (regex == null || pathname.getName().matches(regex)) {
                        results.add(pathname);
                    }
                } else {
                    results.addAll(list(pathname, regex));
                }
                return false;
            }
        });
        return results;
    }
    

    /**
     * 遍历指定文件夹root下的所有符合regex条件名称的File
     * @param root
     * @param regex
     * @return
     */
    public static List<File> list(String root, String regex) {
        return list(new File(root), regex);
    }

    /**
     * 文件拷贝
     * @param fr
     * @param to
     */
    public static void copy(File fr, File to) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new BufferedInputStream(new FileInputStream(fr));
            os = new BufferedOutputStream(new FileOutputStream(to));
            byte[] buf = new byte[1024];
            int i;
            while ((i = is.read(buf)) != -1) {
                os.write(buf, 0, i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件追加内容
     * @param file
     * @param text
     */
    public static void append(File file, String text) {
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            os = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(os, StringUtil.UTF_8));
            bw.write(text);
            bw.close();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 文件追加内容然后换行
     * @param file
     * @param text
     */
    public static void appendln(File file, String text) {
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            os = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(os, StringUtil.UTF_8));
            bw.write(text);
            bw.newLine();
            bw.close();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件换行
     * @param file
     */
    public static void newLine(File file) {
        OutputStream os = null;
        BufferedWriter bw = null;
        try {
            os = new FileOutputStream(file, true);
            bw = new BufferedWriter(new OutputStreamWriter(os, StringUtil.UTF_8));
            bw.newLine();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static String read(File file, String separator) {
        if(separator == null) {
            separator = "";
        }
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, StringUtil.UTF_8);
            br = new BufferedReader(isr);
            String tmp;
            while((tmp = br.readLine()) != null) {
                sb.append(tmp).append(separator);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    
    public static List<String> read(File file) {
        List<String> results = new ArrayList<String>();
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(file);
            isr = new InputStreamReader(is, StringUtil.UTF_8);
            br = new BufferedReader(isr);
            String tmp;
            while((tmp = br.readLine()) != null) {
                results.add(tmp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
                isr.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
    
    /**
     * 创建文件(存在删除再创建)
     * @param file
     */
    public static File newFile(String pathName) {
    	return newFile(new File(pathName));
    }
    
    /**
     * 创建文件(存在删除再创建)
     * @param file
     */
    public static File newFile(File file) {
    	if(file.exists() && file.isFile()) {
    		file.delete();
    	}
    	String filePath = file.getAbsolutePath().replaceAll("\\\\", "/");
    	File tmpDir = new File(filePath.substring(0, filePath.lastIndexOf("/")));
    	if(!tmpDir.exists() || !tmpDir.isDirectory()) {
    		tmpDir.mkdirs();
    	}
    	try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return file;
    }
    
    public static File file(String pathName) {
        File file = new File(pathName);
        if(!file.exists() || file.isDirectory()) {
            file = newFile(file);
        }
        return file;
    }
    
    /**
     * 创建文件夹(存在则不作处理)
     * @param dirPathName
     * @return
     */
    public static File createDir(String dirPathName) {
        return createDir(new File(dirPathName));
    }
    
    /**
     * 创建文件夹(存在则不作处理)
     * @param dir
     * @return
     */
    public static File createDir(File dir) {
        if(!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    /**
     * dir后添加斜杠/(有则不加没则加),其中把反斜杠也更换成斜杠/
     * room/ ==> room/
     * room ==> room/
     * @param dir
     * @return
     */
    public static String dirSlash(String dir) {
        dir = dir.replaceAll("\\\\", "/").trim();
        if(dir.charAt(dir.length() - 1) != '/') {
            dir = dir + "/";
        }
        return dir;
    }
}
