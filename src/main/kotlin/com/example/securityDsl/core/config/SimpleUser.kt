package com.example.securityDsl.core.config

import com.example.securityDsl.core.util.logger

enum class SimpleUser(
    override val title: String,
    val level: Int
): Titled {
    ANONYMOUS("익명", 0),
    USER("일반유저", 10),
    ADMIN("관리자", 100)
    ;

    companion object {

        private val log = logger()

        fun findSimpleUser(title: String): SimpleUser {
            return SimpleUser.valueOf(title.uppercase().trim())
        }

        fun getMoreLevelAuthorities(minAuthority: SimpleUser): String {
            return values().filter { it.level >= minAuthority.level }.joinToString("','") { it.name }
        }
    }
}