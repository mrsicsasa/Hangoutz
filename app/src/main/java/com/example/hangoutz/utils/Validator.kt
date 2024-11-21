package com.example.hangoutz.utils

object Validator {

    fun areAllFieldsFilled(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Array<Boolean> {
        val errors = arrayOf(false, false, false, false)
        if (name.isEmpty()) {
            errors[0] = true
        }

        if (email.isEmpty()) {
            errors[1] = true
        }

        if (password.isEmpty()) {
            errors[2] = true
        }

        if (confirmPassword.isEmpty()) {
            errors[3] = true
        }
        return errors
    }

    fun isValidNameLength(name: String): Boolean {
        return (name.length >= 3 && name.length <= 25)
    }

    fun isValidEmail(email: String): Boolean {
        return (email.endsWith("@gmail.com") == true)
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length >= 8) {
            val regex = Regex(".*\\d.*")
            return regex.containsMatchIn(password)
        }
        return false
    }

    fun doPasswordsMatch(password: String, confirmPassword: String): Boolean {
        return (password == confirmPassword)
    }
}