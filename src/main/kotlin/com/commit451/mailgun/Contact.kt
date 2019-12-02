package com.commit451.mailgun

/**
 * Represents a contact (email + name)
 */
@Suppress("MemberVisibilityCanBePrivate")
class Contact(val email: String, val name: String?) {

    override fun toString(): String {
        if (name != null) {
            return "$name <$email>"
        }
        return email
    }
}