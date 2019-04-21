package com.weibo.plugin

class Logger {

    def static e(def msg) {
        println("【Fat-aar-ERROR】${msg}")
    }

    def static i(def msg) {
        println("【Fat-aar-INFO】${msg}")
    }
}