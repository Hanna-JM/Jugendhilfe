package com.jugendhilfe.jugendhilfe;

import com.jugendhilfe.jugendhilfe.domain.*;
import com.jugendhilfe.jugendhilfe.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

@SpringBootApplication
public class JugendhilfeApplication implements CommandLineRunner {
    @Autowired
    WohngruppenService wohngruppenService;
    @Autowired
    MitarbeiterService mitarbeiterService;
    @Autowired
    FurtherEducationService furtherEducationService;
    @Autowired
    YouthWelfareOfficeService youthWelfareOfficeService;
    @Autowired
    KindService kindService;
    @Autowired
    SorgeberechtigerService sorgeberechtigerService;

    @Autowired
    DokumentationService dokumentationService;

    @Autowired
    DokumentationMAService dokumentationMAService;

    @Autowired
    MitarbeiterDetailService mitarbeiterDetailService;

    @Autowired
    KindDetailService kindDetailService;

    public static void main(String[] args) {
        SpringApplication.run(JugendhilfeApplication.class, args);
    }


    @Bean
    public ViewResolver thymeleafViewResolver() {
        final var resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        final var engine = new SpringTemplateEngine();
        engine.setTemplateResolver(thymeleafTemplateResolver());
        return engine;
    }

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver() {
        final var resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        return resolver;
    }

    @Override
    public void run(String... args) {
        List<Wohngruppe> listWohngruppe = wohngruppenService.findAll();
        if (listWohngruppe.isEmpty()){
            wohngruppenService.addTestWohngruppe();
        }

        List<Mitarbeiter> listMitarbeiter = mitarbeiterService.findAll();
        if (listMitarbeiter.isEmpty()) {
            mitarbeiterService.addTestMitarbeiter();
        }

        List<FurtherEducation> listFurtherEducation = furtherEducationService.findAll();
        if (listFurtherEducation.isEmpty()){
            furtherEducationService.addTestFurtherEducation();
        }

        List<YouthWelfareOffice> listYouthWelfareOffice = youthWelfareOfficeService.findAll();
        if ( listYouthWelfareOffice.isEmpty()){
            youthWelfareOfficeService.addTestYWO();
        }

        List<Kind> listKinder = kindService.listAll();
        if(listKinder.isEmpty()){
            kindService.addTestKind();
        }

        List<Sorgeberechtiger> sorgeberechtigerList = sorgeberechtigerService.listAll();
        if (sorgeberechtigerList.isEmpty()){
            sorgeberechtigerService.addTestSorgeberechtigter();
        }

        List<Dokumentation> dokumentationListKind = dokumentationService.findAll();
        if (dokumentationListKind.isEmpty()){
            dokumentationService.addTestDokumentationKind();
        }

        List<DokumentationMA> dokumentationListMitarbeiter = dokumentationMAService.findAll();
        if (dokumentationListMitarbeiter.isEmpty()){
            dokumentationMAService.addTestDokumentationMitarbeiter();
        }

        List<MitarbeiterDetail> mitarbeiterDetails = mitarbeiterDetailService.findAllAktiveMitarbeiter();
        if (mitarbeiterDetails.isEmpty()){
            mitarbeiterDetailService.addTestMitarbeiterDetail();
        }

        List<KindDetail> kindDetails = kindDetailService.findAllAktiveKind();
        if (kindDetails.isEmpty()){
            kindDetailService.addTestKindDetail();
        }
    } // end run

}
