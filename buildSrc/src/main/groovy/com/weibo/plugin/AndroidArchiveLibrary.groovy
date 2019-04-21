package com.weibo.plugin

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleVersionIdentifier
import org.gradle.api.artifacts.ResolvedArtifact
import org.w3c.dom.Document
import org.w3c.dom.Element

import javax.xml.parsers.DocumentBuilderFactory

class AndroidArchiveLibrary {

    private final Project mProject

    private final ResolvedArtifact mArtifact

    AndroidArchiveLibrary(Project project, ResolvedArtifact artifact) {
        if ("aar" != artifact.getType()) {
            throw new IllegalArgumentException("artifact must be aar type!")
        }
        mProject = project
        mArtifact = artifact
    }

    String getGroup() {
        return getModuleVersionIdentifier().group
    }

    String getName() {
        return getModuleVersionIdentifier().name
    }

    String getVersion() {
        return getModuleVersionIdentifier().version
    }

    private ModuleVersionIdentifier getModuleVersionIdentifier() {
        return mArtifact.moduleVersion.id
    }

    private String getExplodedRootPath() {
        return mProject.buildDir.path + "/intermediates/exploded-aar/"
    }

    File getExplodedRootDir() {
        ModuleVersionIdentifier id = getModuleVersionIdentifier()
        return mProject.file(getExplodedRootPath() + "/" + id.group + "/" + id.name)
    }

    File getRootFolder() {
        ModuleVersionIdentifier id = getModuleVersionIdentifier()
        return mProject.file(getExplodedRootPath() + "/" + id.group + "/" + id.name + "/" + id.version)
    }

    File getAidlFolder() {
        return new File(getRootFolder(), "aidl")
    }

    File getAssetsFolder() {
        return new File(getRootFolder(), "assets")
    }

    File getClassesJarFile() {
        return new File(getRootFolder(), "classes.jar")
    }

    Collection<File> getLocalJars() {
        List<File> localJars = new ArrayList<>()
        File[] jarList = new File(getRootFolder(), "libs").listFiles()
        if (jarList != null) {
            for (File jars : jarList) {
                if (jars.isFile() && jars.getName().endsWith(".jar")) {
                    localJars.add(jars)
                }
            }
        }

        return localJars
    }

    File getJniFolder() {
        return new File(getRootFolder(), "jni")
    }

    File getResFolder() {
        return new File(getRootFolder(), "res")
    }

    File getManifest() {
        return new File(getRootFolder(), "AndroidManifest.xml")
    }

    File getLintJar() {
        return new File(getRootFolder(), "lint.jar")
    }

    List<File> getProguardRules() {
        List<File> list = new ArrayList<>()
        list.add(new File(getRootFolder(), "proguard-rules.pro"))
        list.add(new File(getRootFolder(), "proguard-project.txt"))
        return list
    }

    File getSymbolFile() {
        return new File(getRootFolder(), "R.txt")
    }

    String getPackageName() {
        String packageName = null
        File manifestFile = getManifest()
        if (manifestFile.exists()) {
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance()
                Document doc = dbf.newDocumentBuilder().parse(manifestFile)
                Element element = doc.getDocumentElement()
                packageName = element.getAttribute("package")
            } catch (Exception e) {
                Logger.e(e)
            }
        }
        return packageName
    }
}
