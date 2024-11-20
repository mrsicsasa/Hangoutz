package com.example.hangoutz.ui.screens.registerscreen

object Validator {

    fun registerValidator(name: String, email: String, password: String, confirm: String): Boolean {
        return (isValidNameLength(name) &&
                isValidEmail(email) &&
                isValidPassword(password) &&
                doPasswordsMatch(
                    password,
                    confirm
                ))
    }

    fun areAllFieldsFilled(
        name: String,
        email: String,
        password: String,
        confirm: String
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

        if (confirm.isEmpty()) {
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

    fun isUniqueEmail(): Boolean {
        // Error code 409
        return true
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length >= 8) {
            val regex = Regex(".*\\d.*")
            return regex.containsMatchIn(password)
        }
        return false
    }

    fun doPasswordsMatch(password: String, confirm: String): Boolean {
        return (password == confirm)
    }
}