/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aclass;

import static aclass.AClass.info;
import static aclass.AClass.warning;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Anatol
 * @version 0.1.0.0
 */
public class AFile
{
//---------------------------------------------------------------------------
    /**
     * Метод создаёт новый файл. Если файл с указанным именем уже существует, то происходит
     * исключение.<br />
     *
     * @param fileName имя файла
     * @return ссылка на файл
     * @throws AException в случае ошибки
     */
    public static File createFile(String fileName) throws AException
    {
        if (fileName == null) throw new AException("fileName=null");
        try
        {
            File file = new File(fileName);
            if (file.exists()) throw new AException("File \"" + fileName + "\" alredy exists");
            if (!file.createNewFile()) throw new AException("File \"" + fileName + "\" not created");
            info("createFile", "\"" + fileName + "\"");
            return file;
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    /**
     * Метод возвращает ссылку на файл. Если файла с указанным именем не существует, то происходит
     * исключение.<br />
     *
     * @param fileName имя файла
     * @return ссылка на файл
     * @throws AException в случае ошибки
     */
    public static File openFile(String fileName) throws AException
    {
        warning("openFile", "This function is not developed");
        if (fileName == null) throw new AException("fileName=null");
        File file = new File(fileName);
        if (!file.exists()) throw new AException("File \"" + fileName + "\" not exists");
        info("openFile", "\"" + fileName + "\"");
        return file;
    }
//---------------------------------------------------------------------------
    /**
     * Метод возвращает ссылку на файл. Если файла с указанным именем не существует, то он
     * создаётся.<br />
     *
     * @param fileName
     * @return
     * @throws AException
     */
    public static File openOrCreateFile(String fileName) throws AException
    {
        if (fileName == null) throw new AException("fileName=null");
        try
        {
            File file = new File(fileName);
            if (!file.exists()) if (!file.createNewFile()) throw new AException("File \"" + fileName + "\" not created");
            info("openOrCreateFile", "\"" + fileName + "\"");
            return file;
        }
        catch (IOException ex)
        {
            throw new AException(ex);
        }
    }
//---------------------------------------------------------------------------
    /**
     * Метод удаляет файл. Если файла с указанным именем не существует, то происходит
     * исключение.<br />
     *
     * @param fileName
     * @throws AException
     */
    public static void deleteFile(String fileName) throws AException
    {
        if (fileName == null) throw new AException("fileName=null");
        File file = new File(fileName);
        if (!file.exists()) throw new AException("File \"" + fileName + "\" not exists");
        if (!file.delete()) throw new AException("File \"" + fileName + "\"don't delete");
        info("deleteFile", "\"" + fileName + "\"");
    }
//---------------------------------------------------------------------------
    /**
     * Метод создаёт папку и все промежуточные папки по указанному пути.<br />
     *
     * @param dirName путь
     * @return ссылка на папку
     * @throws AException
     */
    public static File forceDir(String dirName) throws AException
    {
        if (dirName == null) throw new AException("dirName=null");
        File dir = new File(dirName);
        if (!dir.mkdirs()) throw new AException("Dir \"" + dirName + "\" don't create");
        info("forceDir", "\"" + dirName + "\"");
        return dir;
    }
//---------------------------------------------------------------------------
    public static void deleteEmptyDir(String dirName) throws AException
    {
        warning("deleteEmptyDir", "This function is not developed");
        if (dirName == null) throw new AException("dirName=null");
        File dir = new File(dirName);
        if (!dir.exists()) throw new AException("Dir \"" + dirName + "\" not exists");

        info("deleteEmptyDir", "\"" + dirName + "\"");
    }
//---------------------------------------------------------------------------
    public static void deleteDirAndContent(String dirName) throws AException
    {
        warning("deleteDirAndContent", "This function is not developed");
        if (dirName == null) throw new AException("dirName=null");
        File dir = new File(dirName);
        if (!dir.exists()) return;
        deleteDirContent(dir);
        info("deleteDirAndContent", "Dir:" + dir.getName());
        dir.delete();
        info("deleteDirAndContent", "\"" + dirName + "\"");
    }
//---------------------------------------------------------------------------
    private static void deleteDirContent(File dir)
    {
        File fileList[] = dir.listFiles();
        for (File file : fileList)
        {
            if (file.isDirectory()) deleteDirContent(file);
            info("deleteDirContent", file.getName());
            file.delete();
        }
    }
//---------------------------------------------------------------------------
    public static List<String> getFilePathList(String pathName, boolean subdir, final String extList[]) throws AException
    {
        List<String> filePathList = new ArrayList();
        File path = new File(pathName);
        List<String> exts = null;
        if (extList != null) exts = Arrays.asList(extList);
        processFilePach(path, subdir, exts, filePathList);
        return filePathList;
    }
//---------------------------------------------------------------------------
    private static void processFilePach(File path, boolean subdir, final List<String> exts, List<String> filePaths) throws AException
    {
        File pathList[] = path.listFiles();
        if (subdir)
        {
            for (File p : pathList) if (p.isDirectory()) processFilePach(p, subdir, exts, filePaths);
        }
        FilenameFilter filter = new FilenameFilter()
        {
            @Override
            public boolean accept(File file, String string)
            {
                if (exts == null) return true;
                String ext = getExt(string);
                if (ext == null) return false;
                for (String mask : exts) if (ext.equalsIgnoreCase(mask)) return true;
                return false;
            }
        };
        File fileList[] = path.listFiles(filter);
        for (File file : fileList)
        {
            if (!file.isFile()) continue;
            filePaths.add(file.getPath());
        }
    }
//---------------------------------------------------------------------------
    public static List<String> getDirPathList(String pathName, boolean subdir) throws AException
    {
        List<String> dirPathList = new ArrayList();
        File path = new File(pathName);
        if (path == null) throw new AException("path \"" + pathName + "\" not found");
        processDirPach(path, subdir, dirPathList);
        return dirPathList;
    }
//---------------------------------------------------------------------------
    private static void processDirPach(File path, boolean subdir, List<String> filePaths) throws AException
    {
        if (path == null) return;
        File list[] = path.listFiles();
        if (list == null) return;
        if (subdir)
        {
            for (File item : list) if (item.isDirectory()) processDirPach(item, subdir, filePaths);
        }
        for (File item : list) if (item.isDirectory()) filePaths.add(item.getPath());
    }
//---------------------------------------------------------------------------
    public static String getExt(String fileName)
    {
        String ext = "";
        char buf[] = fileName.toCharArray();
        int index = buf.length - 1;
        while (buf[index] != '.')
        {
            if (index <= 0) return null;
            ext = buf[index] + ext;
            index--;
        }
        return ext;
    }
//---------------------------------------------------------------------------
    /**
     * Метод разделяет имя файла на путь, имя и расширение. Если какая либо часть имени файла не
     * определена, она заменяется пустой сторкой. <br />
     *
     * @param fileName имя файла
     * @return массив, в котором [0] - путь, [1] - имя, [2] - расширение.
     */
    public static String[] splitFileName(String fileName)
    {
        String[] list =
        {
            "", "", ""
        };//new String[3];
        int s = fileName.lastIndexOf('/');
        if (s == -1) s = fileName.lastIndexOf('\\');
        if (s != -1)
        {
            list[0] = fileName.substring(0, s);
            fileName = fileName.substring(s + 1);
        }
        s = fileName.lastIndexOf('.');
        if (s != -1)
        {
            list[1] = fileName.substring(0, s);
            list[2] = fileName.substring(s + 1);
        }
        else list[1] = fileName;
        return list;
    }
//---------------------------------------------------------------------------
}
