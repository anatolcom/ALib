/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package anatol.servlet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpRetryException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anatol
 */
public class HttpFunctionController
{
//---------------------------------------------------------------------------
    private final Object target;
    private final Map<String, Method> functionMap = new HashMap<>();
//---------------------------------------------------------------------------
    public HttpFunctionController(Object target)
    {
        this.target = target;
        if (target != null) map(target.getClass());
//        if (target == null) return;
//        System.out.println("------------------------------   mapping   ------------------------------");
//        System.out.println("target class: \"" + target.getClass().getName() + "\"");
////        Method[] mtds = targetClass.getMethods();
//        Method[] mtds = target.getClass().getDeclaredMethods();
//        for (Method method : mtds)
//        {
//            HttpFunction annotation = method.getAnnotation(HttpFunction.class);
//            if (annotation == null) continue;
//            System.out.println("- function: \"" + method.getName() + "\" - pattern: \"" + annotation.pattern() + "\"");
//            functionMap.put(annotation.pattern(), method);
//        }
    }
//---------------------------------------------------------------------------
    public HttpFunctionController(Class<?> targetClass)
    {
        this.target = null;
        map(targetClass);
//        if (targetClass == null) return;
//        System.out.println("------------------------------   mapping   ------------------------------");
//        System.out.println("target class: \"" + targetClass.getName() + "\"");
////        Method[] mtds = targetClass.getMethods();
//        Method[] mtds = targetClass.getDeclaredMethods();
//        for (Method method : mtds)
//        {
//            HttpFunction annotation = method.getAnnotation(HttpFunction.class);
//            if (annotation == null) continue;
//            System.out.println("- function: \"" + method.getName() + "\" - pattern: \"" + annotation.pattern() + "\"");
//            functionMap.put(annotation.pattern(), method);
//        }
    }
//---------------------------------------------------------------------------
    private void map(Class<?> targetClass)
    {
        if (targetClass == null) return;
        System.out.println("------------------------------   mapping   ------------------------------");
        System.out.println("target class: \"" + targetClass.getName() + "\"");
//        Method[] mtds = targetClass.getMethods();
        Method[] mtds = targetClass.getDeclaredMethods();
        for (Method method : mtds)
        {
            HttpFunction annotation = method.getAnnotation(HttpFunction.class);
            if (annotation == null) continue;
            System.out.println("- function: \"" + method.getName() + "\" - pattern: \"" + annotation.pattern() + "\"");
            functionMap.put(annotation.pattern(), method);
        }
    }
//---------------------------------------------------------------------------
    public List<String> getPatterns()
    {
        List<String> list = new ArrayList<>();
        for (String pattern : functionMap.keySet()) list.add(pattern);
        return list;
    }
//---------------------------------------------------------------------------
    private Method find(String pattern, boolean required) throws Exception
    {
        if (pattern == null) throw new IllegalArgumentException("pattern = null");
        Method method = functionMap.get(pattern);
        if (method != null) return method;
        if (required) throw new Exception("method with pattern \"" + pattern + "\" not found");
        return null;
    }
//---------------------------------------------------------------------------
    public boolean hasPath(String path)
    {
        return functionMap.containsKey(path);
    }
//---------------------------------------------------------------------------
    public String getMethodName(String pattern) throws Exception
    {
        return find(pattern, true).getName();
    }
//---------------------------------------------------------------------------
    public void call(String path, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        Method method = find(path, true);
        if (method == null) throw new HttpRetryException("unknow path: " + path, HttpServletResponse.SC_NOT_FOUND);
        try
        {
            method.setAccessible(true);
            method.invoke(target, request, response);
        }
        catch (IllegalAccessException ex)
        {
            throw new Exception("IllegalAccessException " + ex);
        }
        catch (IllegalArgumentException ex)
        {
            throw new Exception("IllegalArgumentException " + ex);
        }
        catch (InvocationTargetException ex)
        {
            Throwable t = ex.getCause();
            if (t instanceof Exception) throw (Exception) t;
            throw new Exception(t);
        }
    }
//---------------------------------------------------------------------------
}
