package com.mycompany.cxacaixpkg;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.mycompany.cxacaixpkg");

        noClasses()
            .that()
            .resideInAnyPackage("com.mycompany.cxacaixpkg.service..")
            .or()
            .resideInAnyPackage("com.mycompany.cxacaixpkg.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.mycompany.cxacaixpkg.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
