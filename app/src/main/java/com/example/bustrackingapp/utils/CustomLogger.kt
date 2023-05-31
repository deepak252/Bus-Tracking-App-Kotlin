package com.example.bustrackingapp.utils

import java.util.logging.Logger

class CustomLogger(
    private val tag : String  = "CustomLogger",
    private val c : String = "", // class name
    private val m : String = "" //method name
) {
    private val logger = Logger.getLogger(tag)
    fun info(msg : Any){
        logger.info("$c $m : $msg")
    }

    fun error(err : Any){
        logger.warning("$c $m : $err")
    }

}