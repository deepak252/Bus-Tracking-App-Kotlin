package com.example.bustrackingapp.core.util

import java.util.logging.Logger

class LoggerUtil(
    private val tag : String  = "CustomLogger",
    private val c : String = "", // class name
) {
    private val logger = Logger.getLogger(tag)
    fun info(msg : Any, fxn:String?=""){
        logger.info("$c $fxn : $msg")
    }

    fun error(err : Any,  fxn:String?=""){
        logger.warning("$c $fxn : $err")
    }

}