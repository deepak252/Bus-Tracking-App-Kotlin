package com.example.bustrackingapp.core.util

object ValidationUtil {
    fun validateName( name : String) : String?{
        if(name.trim().isEmpty()){
            return "Name can't be empty"
        }
        return null
    }

    fun validateEmail( email : String) : String?{
        if(email.trim().isEmpty()){
            return "Email can't be empty"
        }
        val emailRegex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        if(!emailRegex.matches(email)){
            return "Invalid Email"
        }
        return null
    }

    fun validatePhone( phone : String) : String?{
        if(phone.trim().isEmpty()){
            return "Phone number can't be empty"
        }
        val phoneRegex = Regex("^(\\+\\d{1,3}[- ]?)?\\d{10}\$")
        if(!phoneRegex.matches(phone)){
            return "Invalid Phone Number"
        }
        return null
    }

    fun validatePassword( pwd : String) : String?{
        if(pwd.trim().isEmpty()){
            return "Password can't be empty"
        }
        if(pwd.trim().length<4){
            return "Password must contain at least 4 characters"
        }
        if(pwd.trim().length>20){
            return "Password should not contain more than 20 characters"
        }
        return null
    }

    fun validateConfirmPassword( pwd : String, confPwd : String) : String?{
        if(pwd!=confPwd){
            return "Password not match"
        }
        return null
    }
}

