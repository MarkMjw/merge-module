package com.weibo.plugin

import org.gradle.api.Project

/**
 * process jars and classes
 * Created by Vigi on 2017/1/20.
 * Modified by kezong on 2018/12/18
 */
class ExplodedHelper {

    static void processIntoJars(Project project,
                                Collection<AndroidArchiveLibrary> androidLibraries, Collection<File> jarFiles,
                                File folderOut) {
        Logger.i('Merge jars')
        for (androidLibrary in androidLibraries) {
            if (!androidLibrary.rootFolder.exists()) {
                Logger.e('[warning]' + androidLibrary.rootFolder + ' not found!')
                continue
            }
            if (androidLibrary.localJars.isEmpty()) {
                Logger.i("Not found jar file, Library:${androidLibrary.name}")
            } else {
                Logger.i("Merge ${androidLibrary.name} jar file, Library:${androidLibrary.name}")
            }
            androidLibrary.localJars.each {
                Logger.i(it.path)
            }
            project.copy {
                from(androidLibrary.localJars)
                into folderOut
            }
        }
        for (jarFile in jarFiles) {
            if (!jarFile.exists()) {
                Logger.e('[warning]' + jarFile + ' not found!')
                continue
            }
            Logger.i('copy jar from: ' + jarFile)
            project.copy {
                from(jarFile)
                into folderOut
            }
        }
    }

    static void processIntoClasses(Project project,
                                   Collection<AndroidArchiveLibrary> androidLibraries, Collection<File> jarFiles,
                                   File folderOut) {
        Logger.i('Merge classes')
        Collection<File> allJarFiles = new ArrayList<>()
        List<String> rPathList = new ArrayList<>()
        for (androidLibrary in androidLibraries) {
            if (!androidLibrary.rootFolder.exists()) {
                Logger.e('[warning]' + androidLibrary.rootFolder + ' not found!')
                continue
            }
            Logger.i('[androidLibrary]' + androidLibrary.getName())
            allJarFiles.add(androidLibrary.classesJarFile)
            String packageName = androidLibrary.getPackageName()
            if (null != packageName && !packageName.isEmpty()) {
                rPathList.add(androidLibrary.getPackageName())
            }
        }
        for (jarFile in allJarFiles) {
            Logger.i('copy classes from: ' + jarFile)
            project.copy {
                from project.zipTree(jarFile)
                into folderOut
                exclude 'META-INF/'
            }
        }
    }
}
