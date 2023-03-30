# security-dsl
boot 3.0, security 6.0 security config dsl 적용(kotlin)

---

boot 3.0 부터, 정확히는 Security 5.8 부터 HttpSecurityDsl 을 통해 Dsl 로 구성할 수 있다.
<br />(boot 2.latest 는 security 5.7 버전이라서 지원하지 않는다)

---

```kotlin
...
import org.springframework.security.config.annotation.web.invoke  // 요게 포인트! 없으면 http dsl 이 적용안됨
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Configuration
    class BasicSecurity {

        @Bean
        fun basicFilterChain(http: HttpSecurity): SecurityFilterChain {
            http {
                csrf {
                    disable()
                }
                addFilterAfter<BasicAuthenticationFilter>(SimpleApiFilter(BASIC_API_PATH))
                authorizeRequests {
                    authorize(BASIC_API_PATH, hasAnyAuthority(getMoreLevelAuthorities(minAuthority = USER)))
                }
                authorizeRequests {
                    authorize("/**", permitAll)
                }
                httpBasic {  }
            }
            return http.build()
        }
    }
}
```
