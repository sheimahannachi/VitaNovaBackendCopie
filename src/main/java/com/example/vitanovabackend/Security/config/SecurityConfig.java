package com.example.vitanovabackend.Security.config;

import com.example.vitanovabackend.Configuration.ControllerUrls;
import com.example.vitanovabackend.Security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig implements WebSecurityConfigurer {

    @Autowired
    private JwtAuthFilter authFilter;

    // User Creation
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    // Configuring HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .cors() // Enable CORS support
                .and()
                .authorizeHttpRequests()

                .requestMatchers(
                        ControllerUrls.UserUrls.AuthLoginUrl, ControllerUrls.UserUrls.AuthSignupUrl,
                        ControllerUrls.UserUrls.AuthResetPasswordE,ControllerUrls.UserUrls.AuthResetPasswordE,"/api/CheckIpAddress","/api/AddIpAddress","/api/checkPassword",
                        "/api/checkUsername","/api/checkEmail","/api/user/GetUserByUsername","/PG/AddGoal","/api/user/AddUser","/checkout/create-checkout-session","/checkout/process-payment"
                        ,"/api/getuserfromtoken","/api/sendEmail","/api/misc/uploadImage","api/misc/takePic","/api/sendEmailWithAttachment","/api/user/GetUserByEmail","/api/LoginGoogle","/api/GoogleSignup",
                        //yoser
                        "RestController/GetActiveExercise",
                        "RestController/GetExercise",
                        "RestController/Rating/{exerciseId}",
                        "RestController/saveUserExerciseRating/{idEx}/{iduser}",
                        "RestController/ArchiverExercise/{idex}",
                        "RestController/UpdateExercise/{id}",
                        "RestController/addExercise",
                        "RestController/searchEx",
                       "RestController/addPlan",
                       "RestController/UpdatePlan",
                       "RestController/ArchiverPlan/{idplan}",
                       "RestController/GetPlan","/RestController/**",
                       "RestController/getPlan/{id}",
                       "RestController/saveUserExerciseRating/{idEx}",
                       "RestController/getExerciseById/{exerciseId}",
                       "RestController/Rating/{exerciseId}",
                       "RestController/filtered",
                       "RestController/sorted-by-rating",
                       "RestController/addworkoutsession/{id}",
                       "RestController/addSession/{id}/{intensity}",
                       "RestController/statistics",



                        //ons

                        "RestController/addFood",
                        "RestController/updateFood/{id}",
                        "RestController/deleteFood",
                        "RestController/archiveFood/{id}",
                        "RestController/getFood",
                        "RestController/lookup",
                        "RestController/getcalories",
                        "RestController/ListTracker/{id}",
                        "RestController/get-food-cards",
                        "RestController/deleteFoodCard",
                        "RestController/updateFoodCard/{id}",
                        "RestController/addTracker/{id}",
                        "RestController/foodCardCateg",
                        "RestController/ScanBarcode",
                        "RestController/search",
                        "RestController/search-image",
                        "RestController/addHydration/{id}",
                        "RestController/getHydration/{id}",
                        "RestController/hydration/{userId}",
                       //sheima
                        "/PeriodTracker/AddPeriodInformation",
                        "/PeriodTracker/UpdatePeriodinformation",
                        "/PeriodTracker/deletePeriodinformation",
                        "/PeriodTracker/getPeriodTracker",
                        "/PeriodTracker/getPeriodTrackerById/{idPeriod}",
                        "/PeriodTracker/archivePeriod",
                        "/PeriodTracker/ArchivedPeriods",
                        "/PeriodTracker/nonArchivedPeriodTrackers",
                        "/PeriodTracker/periodNotNull",
                        "/PeriodTracker/calculate-cycle-phase",
                        "/PeriodTracker/{idPeriod}/next-period-date",
                        "/PeriodTracker/{idPeriod}/ovulation-date",
                        "/PeriodTracker/getSymptomsAndRatingsForPeriod/{idPeriod}",
                        "/PeriodTracker/exercises",
                        "PeriodTracker/period-food",
                        "PeriodTracker/fertile-window/{idPeriod}",
                        "PeriodTracker/AddJournal",
                        "Notification/unsubscribe/{idUser}",
                        "/Notification/subscribe/{idUser}",
                        "Notification/{Id}",
                        "/Notification/AddNotification",
                        "Notification/getNotification",
                        "Notification/archive/{Id}",
                        "Notification/notifications",
                        //aziz
                        "/Product/addProduct",
                        "/Product/getProductById/{idPr}",
                        "/Product/updateProduct/{idPr}",
                        "/Product/getProducts",
                        "/Product/{idPr}",
                        "/Product/search",
                        "/Product/filter",
                        "/Product/addLike/{idPr}",
                        "/Product/generate-pdf/{cartId}/{userId}",

                        "Cart/Commandeline/{commandelineId}",
                        "Commandeline/{commandelineId}/update",
                        "Cart/Product/{userId}/cart/products/{productId}",
                       " Cart/{userId}/cart/products",
                        "Cart/create/{userId}",
                        "Cart/count/{cartId}",
                        "Cart/commandelines/{idCart}",
                        "Payment/charge",
                        "sendmail/**",




                        //firas
                        ControllerUrls.CommunityUrls.getCommunitiesOrderByChallenger,
                        ControllerUrls.CommunityUrls.addCommunity+"**",
                        ControllerUrls.ChallengesUrl.FindAllActiveChallenge,
                        ControllerUrls.CommunityUrls.UpdateCommunity,
                        ControllerUrls.CommunityUrls.FindAllCommunity,
                        ControllerUrls.CommunicationUrl.getCommunicationByCommunity,
                        ControllerUrls.CommunityUrls.DeleteCommunity,
                        ControllerUrls.CommunityUrls.FindCommunity+"**",
                        ControllerUrls.CommunityUrls.fetchTopThree,
                        ControllerUrls.CommunityUrls.userLeaveCommunity,
                        ControllerUrls.ChallengesUrl.AddChallenge,
                        ControllerUrls.CommunityUrls.getCommunityMembers+"**",
                        ControllerUrls.CommunicationUrl.deleteComunicationUrl,
                        ControllerUrls.CommunicationUrl.updateCommunication,
                        ControllerUrls.CommunicationUrl.seenComunication+"**",
                        ControllerUrls.CommunicationUrl.getComunicationbySenderAndReciever,
                        ControllerUrls.CommunicationUrl.setSeenToCommunicationsOneToOne,
                        ControllerUrls.CommunityUrls.getCommunityByUser+"**",
                        ControllerUrls.CommunityUrls.AddMemberToCommunity+"****",
                        "UploadImage",


                        "/api/user/admin/**","/Cart/**","/Product/**",
                        "/ws/**").permitAll()



                .and()
                .authorizeHttpRequests().requestMatchers("/user/**","/api/signout","/api/**","/api/user/admin/UpdateUser","/api/user/DeleteUser/{{id}}").authenticated()
                .and()

                .authorizeHttpRequests().requestMatchers("/api/user/admin/**").hasRole("ADMIN")
                .and()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Override
    public void init(SecurityBuilder builder) throws Exception {

    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {

    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allowed headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS configuration to all endpoints

        return source;
    }
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("http://localhost:4200"); // Allow requests from this origin
        config.addAllowedMethod("*"); // Allow all HTTP methods
        config.addAllowedHeader("*"); // Allow all headers
        config.setAllowCredentials(true); // Allow credentials (cookies)
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
