package utn.project.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import utn.project.session.AdminSessionFilter;
import utn.project.session.BroadcastSessionFilter;
import utn.project.session.SessionFilter;

@org.springframework.context.annotation.Configuration
@PropertySource("application.properties")
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Autowired
    AdminSessionFilter adminSessionFilterSessionFilter;
    @Autowired
    BroadcastSessionFilter broadcastSessionFilter;

    @Bean
    public FilterRegistrationBean  clientFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);
        registration.addUrlPatterns("/api/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean backOfficeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(adminSessionFilterSessionFilter);
        registration.addUrlPatterns("/office/*");
        return registration;
    }

    @Bean
    public FilterRegistrationBean antennaFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(broadcastSessionFilter);
        registration.addUrlPatterns("/broadcast/*");
        return registration;
    }
}
