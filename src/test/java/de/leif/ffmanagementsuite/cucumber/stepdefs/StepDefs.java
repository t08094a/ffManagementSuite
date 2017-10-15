package de.leif.ffmanagementsuite.cucumber.stepdefs;

import de.leif.ffmanagementsuite.FfManagementSuiteApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = FfManagementSuiteApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
