package com.weibo.plugin

class Logger {

    def static e(def msg) {
        println("【ERROR】${msg}")
    }

    def static i(def msg) {
        println("【INFO】${msg}")
    }
}