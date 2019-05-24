package com.example.demo.Interceptor;

//@EnableWebSecurity
//@Configuration
//public class Securityfilterchain extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//          .withUser("user1").password(passwordEncoder().encode("user1Pass")).roles("USER")
//          .and()
//          .withUser("user2").password(passwordEncoder().encode("user2Pass")).roles("USER")
//          .and()
//          .withUser("admin").password(passwordEncoder().encode("adminPass")).roles("ADMIN")
//          ;
//    }
//
//	    @Override
//	    protected void configure(final HttpSecurity http) throws Exception {
//
//	        http
//	        .authorizeRequests()
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//
//            .and()
//            .httpBasic();
//	    }
//
//	    @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }
//	}



