/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.agarnier.jrobintest;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

/**
 *
 * @author agarnier
 */
public class PackageReflects {

    /**
     * Scans all classloaders for the current thread for loaded jars, and then
     * scans each jar for the package name in question, listing all classes
     * directly under the package name in question. Assumes directory structure
     * in jar file and class package naming follow java conventions
     * (i.e. com.example.test.MyTest would be in /com/example/test/MyTest.class)
     */
    public static Collection<Class> getClassesForPackage(String packageName)
            throws Exception {
        String packagePath = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Set<URL> jarUrls = new HashSet<URL>();

        while (classLoader != null) {
            if (classLoader instanceof URLClassLoader) {
                for (URL url : ((URLClassLoader) classLoader).getURLs()) {
                    if (url.getFile().endsWith(".jar")) // may want better way to detect jar files
                    {
                        jarUrls.add(url);
                    } // if
                } // for
            } // if

            classLoader = classLoader.getParent();
        } // while

        Set<Class> classes = new HashSet<Class>();

        for (URL url : jarUrls) {
            JarInputStream stream = new JarInputStream(url.openStream()); // may want better way to open url connections
            JarEntry entry = stream.getNextJarEntry();

            while (entry != null) {
                String name = entry.getName();

                if (name.endsWith(".class") && name.substring(0,
                        packagePath.length()).equals(packagePath)) {
                    classes.add(Class.forName(name.substring(0,
                            name.length() - 6).replace("/", ".")));
                } // if

                entry = stream.getNextJarEntry();
            } // while

            stream.close();
        } // for

        return classes;
    } // static Collection<Class> getClassesForPackage(String)

    public static String parseParams(Class<?>[] parameters) {
        String result = "";
        for (Class param : parameters) {
            result += result.equals("") ? param.getSimpleName()
                    : "," + param.getSimpleName();
        } // for
        return result;
    } // static String parseParams(Class<?>[])
}
