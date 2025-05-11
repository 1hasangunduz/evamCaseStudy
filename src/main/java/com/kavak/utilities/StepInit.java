package com.kavak.utilities;

import com.kavak.base.BaseTest;
import com.kavak.pages.homepage.KavakHomePage;


public class StepInit extends BaseTest {

    private StepInit() {
        throw new IllegalStateException("Utility class");
    }

    public static final KavakHomePage KAVAK_HOME_PAGE = new KavakHomePage();


}
