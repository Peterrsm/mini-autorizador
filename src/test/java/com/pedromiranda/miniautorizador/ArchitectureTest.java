package com.pedromiranda.miniautorizador;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.pedromiranda.miniautorizador")
public class ArchitectureTest {

    @ArchTest
    public static final ArchRule controller_must_reside_in_impl = classes()
            .that().haveNameMatching(".*ControllerImpl")
            .should().resideInAPackage("..controller.impl..");


    @ArchTest
    public static final ArchRule service_accessed_only_by_controller = layeredArchitecture()
            .consideringAllDependencies()
            .layer("service").definedBy("..controller.impl");

    @ArchTest
    public static final ArchRule controller_must_reside_controller = classes()
            .that()
            .haveNameMatching(".*Controller")
            .should()
            .resideInAPackage("..controller.interfaces");

    @ArchTest
    public static final ArchRule controllerimpl_must_reside_controlle_impl = classes()
            .that()
            .haveNameMatching(".*ControllerImpl")
            .should()
            .resideInAPackage("..controller.impl");
}